package uoption.impl

import cats.kernel.laws.IsEqArrow

trait ImplLaws:
  def foldEmpty[A, B](b: B) = empty[A].fold(b)(_ => ???) <-> b
  def wrapN[A](n: List[Unit], a: A) =
    def rec[T](n: List[Unit], x: T): T = n match
      case _ :: n2 =>
        fold(rec[UOption[T]](n2, wrap(x)))(???)(identity[T])
      case _ => x
    rec(n, a) <-> a
