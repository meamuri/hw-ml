package edu.vsu.hw.ml

import com.linkedin.relevance.isolationforest.IsolationForestModel
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.SparkSession

trait Model {

  val pathToModel: String
  val sparkSession: SparkSession
  val predictionConventions: PredictionConventions

  val schema: StructType = StructType(
    StructField("user_id", StringType, nullable = false) ::
    Nil
  )

  protected final val isolationForestModel: IsolationForestModel = IsolationForestModel.load(pathToModel)

  def predict(features: String): HWPrediction

  def fitModel(data: List[HealthReport])

  def withModel(f: IsolationForestModel => Unit)

}

object Model {
  def apply(predictionConventions: PredictionConventions, sparkSession: SparkSession, pathToModel: String): Model = {
    new ModelImplementation(predictionConventions, sparkSession, pathToModel)
  }

  private class ModelImplementation(override val predictionConventions: PredictionConventions,
                                    override val sparkSession: SparkSession,
                                    override val pathToModel: String) extends Model {
    override def predict(features: String): HWPrediction = {
      val report = predictionConventions.decode(features)
      val reports = List(report).map {
        predictionConventions.healthReportToHealthEntity
      }
      val forPrediction = predictionConventions.toInnerInterpretation(reports)
      val res = isolationForestModel.transform(forPrediction)
      val colValue = predictionConventions.toScore(res.select(outlierColumnName))
      HWPrediction(colValue)
    }

    override def fitModel(data: List[HealthReport]): Unit = {
      val entities = data.map(predictionConventions.healthReportToHealthEntity)
      val dataset = predictionConventions.toInnerInterpretation(entities)
      Runner.instanceIF().fit(dataset).save(pathToModel)
    }

    override def withModel(f: IsolationForestModel => Unit): Unit = {
      f(isolationForestModel)
    }
  }
}
