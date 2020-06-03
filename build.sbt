name := "hw-ml"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.4.5"
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.4.5"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.4.5"
libraryDependencies += "com.linkedin.isolation-forest" % "isolation-forest_2.4.3_2.11" % "1.0.0"
