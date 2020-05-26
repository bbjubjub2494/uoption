package uoption.impl

opaque type UOption[+A] >: UNone.type = A | UNone.type | WrappedUNone
case object UNone
case class WrappedUNone(level: Int):
  assert(level > 0)
  override def toString = "USome(" * level + UNone + ")" * level

def empty[A]: UOption[A] = UNone

def [A](a: A).wrap: UOption[A] = a match
  case UNone => WrappedUNone(1)
  case WrappedUNone(l) => WrappedUNone(l+1)
  case a => a

def [A, B](oa: UOption[A]).fold(ifEmpty: => B)(fa: A => B): B = oa match
  case UNone => ifEmpty
  case WrappedUNone(1) => fa(UNone.asInstanceOf[A])
  case WrappedUNone(l) => fa(WrappedUNone(l-1).asInstanceOf[A])
  case a => fa(a.asInstanceOf[A])

val evSerializable: UOption[Serializable] <:< Serializable =
  summon[UOption[Serializable] <:< Serializable]
