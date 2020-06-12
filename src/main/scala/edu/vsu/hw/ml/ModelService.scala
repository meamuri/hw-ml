package edu.vsu.hw.ml

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.stream.ActorMaterializer
import org.apache.spark.sql.SparkSession
import spray.json.{JsonPrinter, RootJsonWriter}

import scala.concurrent.ExecutionContextExecutor

class ModelService extends App with Service {
  override implicit val system: ActorSystem = ActorSystem()
  override implicit val executor: ExecutionContextExecutor = system.dispatcher
  override implicit val materializer: ActorMaterializer = ActorMaterializer()

  override val sparkSession: SparkSession = SparkSession.builder
    .appName("Spark Isolation Forest")
    .config("spark.master", "local")
    .getOrCreate()
  override implicit def sprayJsonMarshaller[T](implicit writer: RootJsonWriter[T], printer: JsonPrinter): ToEntityMarshaller[T] =
    Dependencies.getSprayJsonMarshaller
  override val predictionConventions: PredictionConventions =
    Dependencies.getPredictionConventionsImplementation

  override val pathToModel: String = "model.data"

  override val logger: LoggingAdapter = Logging(system, getClass)
  Http().bindAndHandle(routes, "0.0.0.0", 9000)
}
