package uoption

import org.scalacheck.Properties
import cats.laws.discipline._
import cats.kernel.laws.discipline._
import cats.instances.all._
import cats.laws.discipline.arbitrary.{given _}
import uoption.instances.{given _}

import org.scalacheck.{Arbitrary, Cogen}

given [A: Arbitrary] as Arbitrary[UOption[A]] = Arbitrary {
  Arbitrary.arbitrary[Option[A]].map {
    case None => UNone
    case Some(a) => USome(a)
  }
}

given [A: Cogen] as Cogen[UOption[A]] =
  Cogen[Option[A]].contramap {
    case UNone => None
    case USome(a) => Some(a)
  }

object UOptionSuite extends Properties("uoption"):
  include(impl.ImplTest[Int, Int].all)

  include(AlignTests[UOption].align[Int, Int, Int, Int].all)
  include(AlternativeTests[UOption].alternative[Int, Int, Int].all)
  include(CoflatMapTests[UOption].coflatMap[Int, Int, Int].all)
  include(CommutativeMonadTests[UOption].commutativeMonad[Int, Int, Int].all)
  include(EqTests[UOption[Int]].eqv.all)
  include(HashTests[UOption[Int]].hash.all)
  include(MonadErrorTests[UOption, Unit].monadError[Int, Int, Int].all)
  include(MonoidTests[UOption[Int]].monoid.all)
  include(OrderTests[UOption[String]].order.all)
  include(PartialOrderTests[UOption[Double]].partialOrder.all)
  include(TraverseTests[UOption].traverse[Int, Int, Int, Set[Int], Option, Option].all)  // TODO: need Commutative parameters
