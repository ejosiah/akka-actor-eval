package co.lazyhacker.actors

import akka.actor.typed.{Behavior, PostStop, Signal}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object IotSupervisor {
  def apply(): Behavior[Nothing] = Behaviors.setup[Nothing](new IotSupervisor(_))
}

class IotSupervisor(context: ActorContext[Nothing]) extends AbstractBehavior[Nothing](context){

  context.log.info("Iot Application started")

  override def onMessage(msg: Nothing): Behavior[Nothing] = {
    Behaviors.unhandled
  }

  override def onSignal: PartialFunction[Signal, Behavior[Nothing]] = {
    case PostStop =>
      context.log.info("Iot Application stopped")
      this
  }
}
