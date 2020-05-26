package uoption

import org.scalacheck.Properties
import cats.kernel.instances.all.{given cats.kernel.Eq[?]}

object UOptionSuite extends Properties("uoption"):
  include(impl.ImplTest[Int, Int].all)
