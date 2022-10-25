package implicits.live

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

object ImplicitsParameters {

  def readCsv(path: String, header: Boolean)(implicit spark: SparkSession) =
    spark.read.option("header", header).csv(path)

  def readJson(path: String)(implicit spark: SparkSession) =
    spark.read.json(path)


  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.toLevel("OFF"))

    implicit val spark: SparkSession = SparkSession.builder
      .master("local[*]")
      .appName("ImplicitsParameters")
      .getOrCreate()

    val dfFromCsv = readCsv("src/main/resources/datasets/offense_codes.csv", true)
    dfFromCsv.show()

    val dfFromJson = readJson("src/main/resources/datasets/currency_rates.json")
    dfFromJson.show()
  }
}
