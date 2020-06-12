package edu.vsu.hw.ml

import com.linkedin.relevance.isolationforest.IsolationForest
import org.apache.spark.sql.{Dataset, SparkSession}

object Runner {
  val contamination = 0.1

  def main(args: Array[String]): Unit = {
    val session = SparkSession
      .builder()
      .appName("Spark Isolation Forest")
      .config("spark.master", "local")
      .getOrCreate()

    val df = session.read.json("features.json")
    df.show()

    val isolationForest = instanceIF()

    val isolationForestModel = isolationForest.fit(df)
    isolationForestModel.write.overwrite.save("model.data")

    session.stop()
  }

  def instanceIF(): IsolationForest = {
    new IsolationForest()
      .setNumEstimators(100)
      .setBootstrap(false)
      .setMaxSamples(256)
      .setMaxFeatures(1.0)
      .setFeaturesCol("features")
      .setPredictionCol("predictedLabel")
      .setScoreCol(outlierColumnName)
      .setContamination(contamination)
      .setContaminationError(0.01 * contamination)
      .setRandomSeed(1)
  }
}
