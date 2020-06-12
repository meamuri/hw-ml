package edu.vsu.hw.ml

import java.time.OffsetDateTime

import com.linkedin.relevance.isolationforest.{IsolationForest, IsolationForestModel}
import org.apache.spark.sql.Dataset

import scala.util.Random

trait Model {

  val pathToModel: String

  protected final val isolationForestModel: IsolationForestModel = IsolationForestModel.load(pathToModel)

  def predict(features: String): HWPrediction

  def fitModel()

  def withModel()

  protected final def toInnerInterpretation(entities: List[HealthReport]): Dataset[_] = {

  }
}

object Model {
  def apply(pathToModel: String): Model = {
    new ModelImplementation(pathToModel)
  }

  private class ModelImplementation(override val pathToModel: String) extends Model {
    override def predict(features: String): HWPrediction = {
      val representation = toInnerInterpretation(List())
      isolationForestModel.transform(representation)
      HWPrediction(25.0)
    }

    override def fitModel(): Unit = {

    }

    override def withModel(): Unit = ???
  }
}
