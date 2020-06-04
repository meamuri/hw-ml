package edu.vsu.hw.ml

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

class ModelService extends App with Service {
  override implicit val system: ActorSystem = ActorSystem()
  override implicit val executor: ExecutionContextExecutor = system.dispatcher
  override implicit val materializer: ActorMaterializer = ActorMaterializer()

  override val logger: LoggingAdapter = Logging(system, getClass)
  Http().bindAndHandle(routes, "0.0.0.0", 9000)
}
