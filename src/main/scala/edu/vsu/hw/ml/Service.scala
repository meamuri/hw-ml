package edu.vsu.hw.ml

import akka.actor.ActorSystem
import akka.stream.Materializer
import org.json4s.DefaultJsonFormats

import scala.concurrent.ExecutionContextExecutor

trait Protocols extends DefaultJsonFormats

trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  val routes = {

  }
}
