name := "hw-ml"

version := "0.1"

scalaVersion := "2.11.12"

enablePlugins(DockerPlugin)

libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "2.4.5"
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.4.5"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.4.5"
libraryDependencies += "com.linkedin.isolation-forest" % "isolation-forest_2.4.3_2.11" % "1.0.0"
libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.12"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.23" // or whatever the latest version is
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.31"

dockerfile in docker := {
  val jarFile = sbt.Keys.`package`
    .in(Compile, packageBin).value

  val classpath = (managedClasspath in Compile).value
  val mainClass = "edu.vsu.hw.ml.ModelService"
  val jarTarget = s"/app/${jarFile.getName}"
  val classpathString = classpath.files.map("/app/" + _.getName)
    .mkString(":") + ":" + jarTarget

  new Dockerfile {
    from("java")
    add(classpath.files, "/app/")
    add(jarFile, jarTarget)
    entryPoint("java", "­cp", classpathString, mainClass)
  }
}
