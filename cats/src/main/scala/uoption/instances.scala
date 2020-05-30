package uoption
package object instances extends Instances

import uoption._

import cats._


private trait EqOption[A: Eq]:
  def eqv(x: UOption[A], y: UOption[A]) = (x, y) match
    case (UNone, UNone) => true
    case (USome(x), USome(y)) => Eq[A].eqv(x, y)
    case _ => false

trait Instances extends Instances0:
  given [A: Order] as Order[UOption[A]]:
    def compare(x: UOption[A], y: UOption[A]): Int = (x, y) match
      case (UNone, UNone) => 0
      case (UNone, _) => -1
      case (_, UNone) => 1
      case (USome(x), USome(y)) => Order[A].compare(x, y)

  given [A: Semigroup] as Monoid[UOption[A]]:
    def empty = UNone
    def combine(x: UOption[A], y: UOption[A]) = (x, y) match
      case (UNone, y) => y
      case (x, UNone) => x
      case (USome(x), USome(y)) => USome(Semigroup[A].combine(x, y))

  // TODO: Alternative, Traverse, CommutativeMonad, CoflatMap, Align
  given MonadError[UOption, Unit]:
    override def map[A, B](x: UOption[A])(f : A => B): UOption[B] = x.map(f)
    def pure[A](a: A): UOption[A] = USome(a)
    def flatMap[A, B](x: UOption[A])(f : A => UOption[B]): UOption[B] = x.flatMap(f)
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

trait Instances0 extends Instances1:
  given [A: PartialOrder] as PartialOrder[UOption[A]]:
    def partialCompare(x: UOption[A], y: UOption[A]): Double = (x, y) match
      case (UNone, UNone) => 0
      case (UNone, _) => -1
      case (_, UNone) => 1
      case (USome(x), USome(y)) => PartialOrder[A].partialCompare(x, y)

trait Instances1 extends Instances2:
  given [A: Hash] as Hash[UOption[A]] with EqOption[A]:
    def hash(x: UOption[A]): Int = x match
      case UNone => x.hashCode
      case USome(x) => Hash[A].hash(x)

trait Instances2:
  given [A: Eq] as Eq[UOption[A]] with EqOption[A]
