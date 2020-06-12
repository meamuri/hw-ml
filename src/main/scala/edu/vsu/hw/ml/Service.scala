package edu.vsu.hw.ml

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.server.directives.PathDirectives.Segment
import akka.http.scaladsl.server.directives.DebuggingDirectives._
import akka.http.scaladsl.server.directives.PathDirectives._
import akka.http.scaladsl.server.directives.MethodDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
import akka.stream.Materializer
import org.json4s.DefaultJsonFormats

import scala.concurrent.ExecutionContextExecutor

trait Protocols extends DefaultJsonFormats

trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  val logger: LoggingAdapter

  val pathToModel: String
  val model: Model = Model.apply(pathToModel)

  val routes = {
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
