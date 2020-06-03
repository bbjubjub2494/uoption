package uoption.impl

case object UNone
case class WrappedUNone[+A](unwrap: A)

type USome[+A] = A | WrappedUNone[A]
type UOption[+A] = UNone.type | USome[A]

def empty[A]: UOption[A] = UNone

inline def [A](a: A).wrap: USome[A] = a match
  case UNone => WrappedUNone(a)
  case a: WrappedUNone[_] => WrappedUNone(a)
  case a => a

inline def [A, B](oa: UOption[A]).fold(inline ifEmpty: B)(fa: A => B): B = oa match
  case UNone => ifEmpty
  case WrappedUNone(a) => fa(a.asInstanceOf[A])
  case a => fa(a.asInstanceOf[A])
