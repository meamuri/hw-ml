package edu.vsu.hw.ml

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
//import akka.http.scaladsl.server.PathMatchers.Segment
//import akka.http.scaladsl.server.directives.PathDirectives.Segment
import akka.http.scaladsl.server.directives.DebuggingDirectives._
import akka.http.scaladsl.server.directives.PathDirectives._
import akka.http.scaladsl.server.directives.MethodDirectives._
import akka.stream.Materializer
import org.json4s.DefaultJsonFormats

import scala.concurrent.ExecutionContextExecutor

trait Protocols extends DefaultJsonFormats

trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  val logger: LoggingAdapter

  val routes = {
    logRequestResult("model-service") {
      pathPrefix("predict")
        (get & pathSegment(Segment)) {
          features: String =>
        }
    }
  }
}