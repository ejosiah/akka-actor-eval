package co.lazyhacker.actors

import akka.actor.typed.{ActorSystem, Behavior, PostStop, Signal}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object StartStopActor{
  def apply(): Behavior[String] = Behaviors.setup(context => new StartStopActor(context))
}

class StartStopActor(context: ActorContext[String]) extends AbstractBehavior[String](context){

  println("first started")
  context.spawn(StartStopActor2(), "second")

  override def onMessage(msg: String): Behavior[String] = msg match {
    case "stop" => Behaviors.stopped
  }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("first stopped")
      this
  }
}

object StartStopActor2{
  def apply(): Behavior[String] = Behaviors.setup(context => new StartStopActor2(context))
}

class StartStopActor2(context: ActorContext[String]) extends AbstractBehavior[String](context){

  println("second actor started")

  override def onMessage(msg: String): Behavior[String] =  {
    Behaviors.unhandled
  }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("second stopped")
      this
  }
}

object Main0{
  def apply(): Behavior[String] = Behaviors.setup(new Main0(_))
}

class Main0(context: ActorContext[String]) extends AbstractBehavior[String](context){
  override def onMessage(msg: String): Behavior[String] = msg match {
    case "start" =>
      val first = context.spawn(StartStopActor(), "first")
      first ! "stop"
      this
  }
}

object LifeCycle extends App {
  val testSystem = ActorSystem(Main0(), "testSystem")
  testSystem ! "start"

  Thread.sleep(1000)
  testSystem.terminate()
}
