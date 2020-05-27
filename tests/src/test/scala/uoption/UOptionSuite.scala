package uoption

import org.scalacheck.Properties
import cats.instances.all.{given cats.Eq[?]}

object UOptionSuite extends Properties("uoption"):
  include(impl.ImplTest[Int, Int].all)
