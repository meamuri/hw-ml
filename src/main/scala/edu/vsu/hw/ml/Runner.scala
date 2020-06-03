package edu.vsu.hw.ml

import com.linkedin.relevance.isolationforest.IsolationForest
import org.apache.spark.sql.SparkSession

object Runner {
  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder().getOrCreate()
    val df = session.read.json("")
    val forest = new IsolationForest()
    val model = forest.fit(df)
    model.save("boooop")
    println("woosh")
  }
}
