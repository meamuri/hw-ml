package edu.vsu.hw.ml

import com.linkedin.relevance.isolationforest.IsolationForest

trait Model {

  val pathToModel: String

  protected final val isolationForest = IsolationForest.load(pathToModel)

  def predict(features: String)

  def fitModel()

  def withModel()
}

object Model {
  def apply(pathToModel: String): Model = {
    new ModelImplementation(pathToModel)
  }

  private class ModelImplementation(override val pathToModel: String) extends Model {
    override def predict(features: String): Unit = {
      ""
    }

    override def fitModel(): Unit = ???

    override def withModel(): Unit = ???
  }
}
