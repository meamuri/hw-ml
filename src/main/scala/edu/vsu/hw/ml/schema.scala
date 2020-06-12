package edu.vsu.hw.ml

import java.time.OffsetDateTime

import org.apache.spark.sql.DataFrame

trait Col[Self]

sealed trait UserId extends Col[UserId]
object UserId extends UserId

sealed trait Features extends Col[Features]
object Features extends Features

sealed trait Score extends Col[Score]
object Score extends Score

case class Dataset[Schema](df: DataFrame) {
  def validate[T <: Col[T]](
    col: Col[T],
    name: String
  ): Dataset[Schema with T] = ???
}

final case class HealthReport(userId: String, timestamp: OffsetDateTime, metrics: List[Metric])

final case class Metric(name: String, value: Any)
