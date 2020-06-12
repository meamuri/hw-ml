package edu.vsu.hw.ml

import java.time.OffsetDateTime

final case class HealthReport(userId: String, timestamp: OffsetDateTime, metrics: List[Metric])

final case class Metric(name: String, value: Any)
