package edu.vsu.hw.ml

import java.time.OffsetDateTime
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

final case class HealthReport(userId: String, timestamp: OffsetDateTime, metrics: List[Metric])
final case class Metric(name: String, value: Any)

case class HWPrediction(value: Double)
trait Protocols extends DefaultJsonProtocol {
  implicit val predictionFormat: RootJsonFormat[HWPrediction] = jsonFormat1(HWPrediction.apply)
}
