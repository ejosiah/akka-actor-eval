package co.lazyhacker.actors

import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext}
import akka.actor.typed.{ActorRef, Behavior, PostStop, Signal}

object Device {
  sealed trait Command
  final case class ReadTemperature(requestId: Long, replyTo: ActorRef[RespondTemperature]) extends Command
  final case class RespondTemperature(requestId: Long, value: Option[Double]) extends Command

  def apply(groupId: String, deviceId: String): Behavior[Command] = ???
}

class Device(context: ActorContext[Device.Command], groupId: String, deviceId: String) extends AbstractBehavior[Device.Command](context){
  import Device._

  var lastTemperatureReading: Option[Double] = None

  context.log.info("Device actor {} - {}", Array[Object](groupId, deviceId))

  override def onMessage(msg: Command): Behavior[Command] = {
    msg match {
      case ReadTemperature(id, replyTo) =>
        replyTo ! RespondTemperature(id, lastTemperatureReading)
        this
    }
  }

  override def onSignal: PartialFunction[Signal, Behavior[Command]] = {
    case PostStop =>
      context.log.info("Device actor {}-{} stopped", Array[Object](groupId, deviceId))
      this
  }
}
