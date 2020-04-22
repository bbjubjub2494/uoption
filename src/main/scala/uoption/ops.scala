package uoption

trait UOptionOps[A] extends Any with IterableOnce[A]:
  def get: A

case object UNone extends UOptionOps[Nothing]:
  override def knownSize = 0
  def iterator = Iterator.empty
  def get: Nothing = throw NoSuchElementException("UNone.get")

case class USome[A](a: A) extends AnyVal with UOptionOps[A]:
  override def knownSize = 1
  def iterator: Iterator[A] = Iterator.single(a)
  def get: A = a

opaque type UOption[+A] >: UNone.type = UOptionImpl[A]

object UOption:
  extension on [A](self: UOption[A]) {
    def isEmpty = self.isInstanceOf[UNone.type]
    def get = self.fold(UNone.get)(a => a)
    def iterator: Iterator[A] = self.fold(Iterator.empty)(Iterator.single(_))
  }

  def apply[A](a: A | Null) =
    if a == null
    then UNone
    else a.wrap
