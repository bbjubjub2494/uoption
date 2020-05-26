package uoption

import org.scalacheck._
import Prop.forAll

import cats.kernel.Eq
import cats.kernel.laws.SerializableLaws
import org.typelevel.discipline.Laws

object SerializableTests extends Laws:
  import SerializableLaws.serializable

  def apply[A: Arbitrary] =
    new SimpleRuleSet("serialization",
      "errorless" -> forAll(serializable[A]),
    )
