package edu.vsu.hw.ml

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.marshalling.{ToEntityMarshaller, ToResponseMarshallable}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.PathDirectives.Segment
import akka.http.scaladsl.server.directives.DebuggingDirectives._
import akka.http.scaladsl.server.directives.PathDirectives._
import akka.http.scaladsl.server.directives.MethodDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
import akka.stream.Materializer
import spray.json.{CompactPrinter, JsonPrinter, RootJsonWriter}

import scala.concurrent.ExecutionContextExecutor


trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  val logger: LoggingAdapter

  implicit def sprayJsonMarshaller[T](
    implicit writer: RootJsonWriter[T],
    printer: JsonPrinter = CompactPrinter):
  ToEntityMarshaller[T]

  val pathToModel: String
  val model: Model = Model.apply(pathToModel)

  val routes: Route = {
    logRequestResult("model-service") {
      pathPrefix("predict")
        (post & path(Segment)) {
          features: String =>
            complete {
              ToResponseMarshallable(model.predict(features))
            }
        }
    }
  }
}
