package uoption

import impl.{wrap, fold}
opaque type UOption[+A] = impl.UOption[A]
private type Sub[A] >: impl.UOption[A] <: UOption[A]
private type Sup[A] >: UOption[A] <: impl.UOption[A]

val UNone: UOption[Nothing] = impl.UNone

object USome:
  def unapply[A](oa: UOption[A]): Option[A] =
    fold[A, Option[A]](oa: Sup[A])(None: Option[A])(a => Some(a))
  def apply[A](a: A): UOption[A] = wrap(a): Sub[A]

object UOption:
  def apply[A](a: A | Null): UOption[A] = if a == null then UNone else wrap(a)
  def empty[A]: UOption[A] = UNone

  extension UOptionOps on [A](self: UOption[A]):
    def isDefined = self.fold(false)(_ => true)
    def isEmpty = !isDefined

    def get: A = fold(self)(throw NoSuchElementException("UNone.get"))(a => a)
    def iterator: Iterator[A] = fold(self)(Iterator.empty)(Iterator.single)

  extension UOptionExtractOps on [A, B >: A](self: UOption[A]):
    def getOrElse(default: => B): B = fold[A, B](self)(default)(a => a: B)
    def toArray(using reflect.ClassTag[B]): Array[B] = fold[A, Array[B]](self)(Array.empty)(a => Array(a))

  extension UOptionFlattenOps on [A <: UOption[B], B](self: UOption[A]):
    def flatten: UOption[B] = fold[A, UOption[B]](self)(UNone)(a => a)

  extension UOptionNullOps on [A, B >: A | Null](self: UOption[A]):
    def orNull: B = fold[A, B](self)(null)(a => a: B)

  extension UOptionMapOps on [A, B](self: UOption[A]):
    def map(f: A => B): UOption[B] = fold(self)(UNone)(a => wrap(f(a)))
    def flatMap(f: A => UOption[B]): UOption[B] = fold(self)(UNone)(a => f(a))

  /**
   * [UOption] values are serializable unless they wrap a value that isn't.
   *
   * Note that some values such as those of type `USome[Int]` can be safely serialized despite the fact that their type doesn't indicate it.
   */
  given uoptionIsSerializableUOption as (UOption[Serializable] <:< Serializable) = <:<.refl
