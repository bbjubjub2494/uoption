package uoption

opaque type UOption[+A] = A | impl.UNone.type | impl.WrappedUNone

object impl:
  private[uoption] case object UNone
  private[uoption] case class WrappedUNone(level: Int):
    assert(level > 0)

  def [A](self: A).wrap: UOption[A] = self.match {
    case UNone => WrappedUNone(1)
    case WrappedUNone(level) => WrappedUNone(level+1)
    case a => a
  }

  // TODO: inline
  def [A,B](self: UOption[A]).fold(ifEmpty: => B)(f: A => B): B = self.match {
    case UNone => ifEmpty
    case _ => f(self.match {
      case WrappedUNone(1) => UNone
      case WrappedUNone(level) => WrappedUNone(level-1)
      case a => a
    }.asInstanceOf[A])
  }
