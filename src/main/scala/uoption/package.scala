package uoption

val UNone: UOption[Nothing] = internals.UNone

object USome:
  def unapply[A](oa: UOption[A]): Option[A] =
    fold[A, Option[A]](oa)(a => Some(a))(None: Option[A])
  def apply[A](a: A): UOption[A] = wrap[A](a)

extension UOptionOps on [A](self: UOption[A]):
  def isDefined = self.fold(_ => true)(false)
  def isEmpty = !isDefined
