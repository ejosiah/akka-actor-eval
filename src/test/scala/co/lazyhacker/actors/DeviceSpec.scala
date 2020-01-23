package co.lazyhacker.actors

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import Device._
import org.scalatest.WordSpecLike
import org.scalatest.junit.JUnitRunner

class DeviceSpec extends ScalaTestWithActorTestKit with WordSpecLike {

  "Device actor" should {
    "reply with empty reading if no temperature is known" in {
      val probe = createTestProbe[RespondTemperature]()
      var deviceActor = spawn(Device("group", "device"))

      deviceActor ! ReadTemperature(requestId =  42, probe.ref)
      val response = probe.receiveMessage()
      response.requestId should === (42)
      response.value === (None)
      true === (false)
    }
  }
}
