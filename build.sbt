name := "ScalaLessons"

version := "0.1"

scalaVersion := "2.13.8"

val sparkVersion = "3.2.0"

resolvers += "Nexus local" at "https://nexus-repo.dmp.vimpelcom.ru/repository/sbt_releases_/"
resolvers += "Nexus proxy" at "https://nexus-repo.dmp.vimpelcom.ru/repository/maven_all_/"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.spark" %% "spark-avro" % sparkVersion,
  "com.hortonworks.registries" % "schema-registry-serdes" % "0.9.1"
)
  .map(_.exclude("org.slf4j","log4j-over-slf4j"))

addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3")

useCoursier := false
scalacOptions += "-Ymacro-annotations"
scalacOptions += "-deprecation"

assemblyMergeStrategy in assembly := {
  case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
