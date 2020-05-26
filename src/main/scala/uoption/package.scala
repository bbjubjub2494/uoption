package uoption

val UNone: UOption[Nothing] = impl.UNone

object USome:
  def unapply[A](oa: UOption[A]): Option[A] =
    fold[A, Option[A]](oa)(None: Option[A])(a => Some(a))
  def apply[A](a: A): UOption[A] = wrap(a)

object UOption:
  def apply[A](a: A | Null) = if a == null then UNone else a.wrap

extension UOptionOps on [A](self: UOption[A]):
  def isDefined = self.fold(false)(_ => true)
  def isEmpty = !isDefined

  def get = self.fold(throw NoSuchElementException("UNone.get"))(a => a)

extension UOptionExtractOps on [A, B >: A](self: UOption[A]):
  def getOrElse(default: => B): B = fold[A, B](self)(default)(a => a: B)
  def toArray(using reflect.ClassTag[B]): Array[B] = fold[A, Array[B]](self)(Array.empty)(a => Array(a))

extension UOptionFlattenOps on [A <: UOption[B], B](self: UOption[A]):
  def flatten: UOption[B] = fold[A, UOption[B]](self)(UNone)(a => a)

extension UOptionNullOps on [A, B >: A | Null](self: UOption[A]):
  def orNull: B = fold[A, B](self)(null)(a => a: B)

extension UOptioMapOps on [A, B](self: UOption[A]):
  def map(f: A => B): UOption[B] = fold(self)(UNone)(a => wrap(f(a)))
  def flatMap(f: A => UOption[B]): UOption[B] = fold(self)(UNone)(a => f(a))
