package uoption

import org.scalacheck._
import cats.kernel.instances.all.{given cats.kernel.Eq[?]}

given [A: Arbitrary] as Arbitrary[UOption[A]] = Arbitrary {
  Arbitrary.arbitrary[Option[A]].map {
    case Some(x) => USome(x)
    case None => UNone
  }
}

object UOptionSuite extends Properties("uoption"):
  include(impl.ImplTest[Int, Int].all)
  include(SerializableTests[UOption[UOption[UOption[UOption[Int]]]]].all)

  property("regression test for #7") =
    USome(null) match
      case USome(null) => true
      case _ => false
