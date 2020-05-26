package uoption

val UNone: UOption[Nothing] = internals.UNone

object USome:
  def unapply[A](oa: UOption[A]): Option[A] =
    fold[A, Option[A]](oa)(a => Some(a))(None: Option[A])
  def apply[A](a: A): UOption[A] = wrap(a)

object UOption:
  def apply[A](a: A | Null) = if a == null then UNone else a.wrap

extension UOptionOps on [A](self: UOption[A]):
  def isDefined = self.fold(_ => true)(false)
  def isEmpty = !isDefined

  def get = self.fold(a => a)(throw NoSuchElementException("UNone.get"))

extension UOptionExtractOps on [A, B >: A](self: UOption[A]):
  def getOrElse(default: => B): B = fold[A, B](self)(a => a: B)(default)
  def toArray(using reflect.ClassTag[B]): Array[B] = fold[A, Array[B]](self)(a => Array(a))(Array.empty)

extension UOptionFlattenOps on [A <: UOption[B], B](self: UOption[A]):
  def flatten: UOption[B] = fold[A, UOption[B]](self)(a => a)(UNone)

extension UOptionNullOps on [A, B >: A | Null](self: UOption[A]):
  def orNull: B = fold[A, B](self)(a => a: B)(null)

extension UOptioMapOps on [A, B](self: UOption[A]):
  def map(f: A => B): UOption[B] = fold(self)(a => wrap(f(a)))(UNone)
  def flatMap(f: A => UOption[B]): UOption[B] = fold(self)(a => f(a))(UNone)
