package co.lazyhacker.actors

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

class SpecTest extends WordSpec with Matchers{

  "Spec" should {
    "do stuff" in {
      true should === (true)
    }
  }
}
