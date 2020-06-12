package edu.vsu.hw.ml

import akka.http.scaladsl.marshalling.ToEntityMarshaller
import spray.json.{JsonPrinter, RootJsonWriter}

object Dependencies {
  def getSprayJsonMarshaller[T](implicit writer: RootJsonWriter[T], printer: JsonPrinter): ToEntityMarshaller[T] = ???
  def getPredictionConventionsImplementation: PredictionConventions = ???
}
