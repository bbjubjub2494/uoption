package uoption

type UOptionImpl[+A] = A | UNone.type | WrappedUNone

private case class WrappedUNone(level: Int):
  assert(level > 0)

def [A](self: A).wrap: UOptionImpl[A] = self.match {
  case UNone => WrappedUNone(1)
  case WrappedUNone(level) => WrappedUNone(level+1)
  case a => a
}

def [A,B](self: UOptionImpl[A]).fold(ifEmpty: => B)(f: A => B): B = self.match {
  case UNone => ifEmpty
  case _ => f(self.match {
    case WrappedUNone(1) => UNone
    case WrappedUNone(level) => WrappedUNone(level-1)
    case a => a
  }.asInstanceOf[A])
}
