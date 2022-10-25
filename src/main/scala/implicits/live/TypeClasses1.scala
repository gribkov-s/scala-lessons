package implicits.live

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Row, SparkSession}
import org.json4s.{JObject, JString}
import org.json4s.JsonDSL._

import scala.util.parsing.json.JSONObject


object TypeClasses1 {

  trait JsonEncoder[T] {
    def encode(entity: T): String
  }

  object JsonEncoder {

    implicit val rowEncoder: JsonEncoder[Row] =
      new JsonEncoder[Row] {
        override def encode(entity: Row): String = {
          val data = entity.getValuesMap(entity.schema.fieldNames)
          JSONObject(data).toString()
        }
      }

    implicit val curRateRecEncoder: JsonEncoder[CurrencyRateRec] =
      new JsonEncoder[CurrencyRateRec] {
        override def encode(entity: CurrencyRateRec): String = {
          val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
          val dateUnix = LocalDate.parse(entity.date, formatter).toEpochDay
          val data = Map(
            "date" -> dateUnix,
            "code" -> "%03d".format(entity.code),
            "name" -> entity.name.toUpperCase,
            "rate" -> entity.rate
          )
          JSONObject(data).toString()
        }
      }
  }

  def toJson[T](entity: T)(implicit ev: JsonEncoder[T]): String = ev.encode(entity)


  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.toLevel("OFF"))

    val spark: SparkSession = SparkSession.builder
      .master("local[*]")
      .appName("TypeClasses1")
      .getOrCreate()

    import spark.implicits._

    val currencyRatesDf = currencyRates.toDF()
    val currencyRatesDs = currencyRates.toDS()

    val currencyRatesJsonDf = currencyRatesDf.map(toJson(_))
    currencyRatesJsonDf.show(truncate = false)

    val currencyRatesJsonDs = currencyRatesDs.map(toJson(_))
    currencyRatesJsonDs.show(truncate = false)
  }
}
