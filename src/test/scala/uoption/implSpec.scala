package uoption

import org.scalacheck._
import Gen._, Prop._

object implSpec extends Properties("unboxed option implementation"):
  property("folding UNone is correct") =
    forAll { (a: Boolean) =>
      Prop.?=(UNone.fold(a)(_ => ???), a)
    }

  property("wrapping, then folding anything is identity") =
    forAll(choose(0, 64), oneOf(UNone, ())) { (n, a) =>
      def rec[A](n: Int, a: A): A =
        if n > 0 then
          rec[UOptionImpl[A]](n-1, a.wrap).fold(???)(a => a)
        else
          a
      Prop.?=(UNone, rec(n, UNone))
    }
