package uoption.impl

case object UNone
case class WrappedUNone[+A](level: Int):
  assert(level > 0)
  override def toString = "USome(" * level + UNone + ")" * level

type USome[+A] = A | WrappedUNone[A]
type UOption[+A] = UNone.type | USome[A]

def empty[A]: UOption[A] = UNone

inline def [A](a: A).wrap: USome[A] = a match
  case UNone => WrappedUNone(1)
  case WrappedUNone(l) => WrappedUNone(l+1)
  case a => a

inline def [A, B](oa: UOption[A]).fold(inline ifEmpty: B)(fa: A => B): B = oa match
  case UNone => ifEmpty
  case WrappedUNone(1) => fa(UNone.asInstanceOf[A])
  case WrappedUNone(l) => fa(WrappedUNone(l-1).asInstanceOf[A])
  case a => fa(a.asInstanceOf[A])

val evSerializable: UOption[Serializable] <:< Serializable =
  summon[UOption[Serializable] <:< Serializable]
