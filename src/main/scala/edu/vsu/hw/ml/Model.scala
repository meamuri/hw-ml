package edu.vsu.hw.ml

import com.linkedin.relevance.isolationforest.{IsolationForest, IsolationForestModel}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Dataset, SparkSession}

trait Model {

  val pathToModel: String
  val sparkSession: SparkSession

  val schema: StructType = StructType(
    StructField("user_id", StringType, nullable = false) ::
    Nil
  )

  protected final val isolationForestModel: IsolationForestModel = IsolationForestModel.load(pathToModel)

  def predict(features: String): HWPrediction

  def fitModel(data: List[HealthReport])

  def withModel(f: IsolationForestModel => Unit)

  protected final def toInnerInterpretation(entities: List[HealthReport]): Dataset[HealthReport] = {
    Runner.dataToDataset(sparkSession, entities)
  }
}

object Model {
  def apply(sparkSession: SparkSession, pathToModel: String): Model = {
    new ModelImplementation(sparkSession, pathToModel)
  }

  private class ModelImplementation(override val sparkSession: SparkSession,
                                    override val pathToModel: String) extends Model {
    override def predict(features: String): HWPrediction = {
      val representation = toInnerInterpretation(List())
      val res = isolationForestModel.transform(representation)
      val col = res.select(outlierColumnName)
      HWPrediction(25.0)
    }

    override def fitModel(data: List[HealthReport]): Unit = {
      Runner.instanceIF().fit(toInnerInterpretation(data)).save(pathToModel)
    }

    override def withModel(f: IsolationForestModel => Unit): Unit = {
      f(isolationForestModel)
    }
  }
}
