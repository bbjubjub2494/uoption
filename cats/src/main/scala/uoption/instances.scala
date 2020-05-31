package uoption
package object instances extends Instances

import uoption._

import cats._
import cats.data.Ior


private trait EqOption[A: Eq]:
  def eqv(x: UOption[A], y: UOption[A]) = (x, y) match
    case (UNone, UNone) => true
    case (USome(x), USome(y)) => Eq[A].eqv(x, y)
    case _ => false

trait Instances extends Instances0:
  given uoptionCatsOrderForUOption[A: Order] as Order[UOption[A]]:
    def compare(x: UOption[A], y: UOption[A]): Int = (x, y) match
      case (UNone, UNone) => 0
      case (UNone, _) => -1
      case (_, UNone) => 1
      case (USome(x), USome(y)) => Order[A].compare(x, y)

  given uoptionCatsMonoidForUOption[A: Semigroup] as Monoid[UOption[A]]:
    inline def empty = UNone
    def combine(x: UOption[A], y: UOption[A]) = (x, y) match
      case (UNone, y) => y
      case (x, UNone) => x
      case (USome(x), USome(y)) => USome(Semigroup[A].combine(x, y))

  given uoptionCatsShowForUOption[A: Show] as Show[UOption[A]]:
    def show(x: UOption[A]) = x match
      case UNone => UNone.toString
      case USome(x) => "USome(" + Show[A].show(x) + ")"

  given uoptionCatsInstancesForUOption as MonadError[UOption, Unit] with Align[UOption] with Alternative[UOption] with CoflatMap[UOption] with CommutativeMonad[UOption] with Traverse[UOption]:
    inline override def map[A, B](x: UOption[A])(f : A => B): UOption[B] = x.map(f)
    inline def pure[A](a: A): UOption[A] = USome(a)
    inline def flatMap[A, B](x: UOption[A])(f : A => UOption[B]): UOption[B] = x.flatMap(f)
    def tailRecM[A, B](a: A)(f: A => UOption[Either[A, B]]): UOption[B] =
      @annotation.tailrec
      def rec(a: A): UOption[B] = f(a) match
        case UNone => UNone
        case USome(Left(a)) => rec(a)
        case USome(Right(b)) => USome(b)
      rec(a)
    def handleErrorWith[A](x: UOption[A])(f: Unit => UOption[A]): UOption[A] = x match
      case UNone => f(())
      case x => x

    def raiseError[A](e: Unit): UOption[A] = UNone

    def align[A, B](x: UOption[A], y: UOption[B]): UOption[Ior[A, B]] = (x, y) match
      case (UNone, UNone) => UNone
      case (USome(a), UNone) => USome(Ior.Left(a))
      case (UNone, USome(b)) => USome(Ior.Right(b))
      case (USome(a), USome(b)) => USome(Ior.Both(a, b))
    inline def functor = this

    inline def coflatMap[A, B](x: UOption[A])(f: UOption[A] => B) =
      USome(f(x))

    def foldLeft[A, B](x: UOption[A], b: B)(f: (B, A) => B): B = x match
      case UNone => b
      case USome(a) => f(b, a)

    def foldRight[A, B](x: UOption[A], b: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] = x match
      case UNone => b
      case USome(a) => f(a, b)

    inline def empty[A]: UOption[A] = UNone

    inline def combineK[A](x: UOption[A], y: UOption[A]): UOption[A] = x.orElse(y)

    def traverse[G[_]: Applicative, A, B](x: UOption[A])(f: A => G[B]): G[UOption[B]] = x match
      case UNone => Applicative[G].pure(UNone)
      case USome(a) => Applicative[G].map(f(a))(b => USome(b))

trait Instances0 extends Instances1:
  given uoptionCatsPartialOrderForUOption[A: PartialOrder] as PartialOrder[UOption[A]]:
    def partialCompare(x: UOption[A], y: UOption[A]): Double = (x, y) match
      case (UNone, UNone) => 0
      case (UNone, _) => -1
      case (_, UNone) => 1
      case (USome(x), USome(y)) => PartialOrder[A].partialCompare(x, y)

trait Instances1 extends Instances2:
  given uoptionCatsHashForUOption[A: Hash] as Hash[UOption[A]] with EqOption[A]:
    def hash(x: UOption[A]): Int = x match
      case UNone => x.hashCode
      case USome(x) => Hash[A].hash(x)

trait Instances2:
  given uoptionCatsEqForUOption[A: Eq] as Eq[UOption[A]] with EqOption[A]
