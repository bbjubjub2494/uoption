package uoption

import impl._
val UNone: UOption[Nothing] = impl.UNone

object USome:
  def apply[A](a: A): UOption[A] = a.wrap
  def unapply[A](self: UOption[A]): Option[A] = impl.fold[A, Option[A]](self)(None)(Some[A])

object UOption:
  extension on [A](self: UOption[A]):
    def isEmpty = self.fold(true)(_ => false)
    def isDefined = self.fold(false)(_ => true)
    def getOrElse(default: A) = self.fold(default)(a => a)
    def get = self.fold(throw NoSuchElementException("UNone.get"))(a => a)
    //def iterator: Iterator[A] = self.fold[A](Iterator.empty)(Iterator.single)

  extension on [A, B](self: UOption[A]):
    def map(f: A => B): UOption[B] = self.fold(UNone)(a => USome(f(a)))

  def apply[A](a: A | Null) =
    if a == null
    then UNone
    else a.wrap
