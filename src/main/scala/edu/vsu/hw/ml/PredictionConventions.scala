package edu.vsu.hw.ml

import org.apache.spark.sql.Dataset

trait PredictionConventions {
  def healthReportToHealthEntity(r: HealthReport): HealthEntity
  def toScore(dataset: Dataset[_]): Double
  def toInnerInterpretation(reports: List[HealthEntity]): Dataset[HealthEntity]
  def decode(json: String): HealthReport
}
