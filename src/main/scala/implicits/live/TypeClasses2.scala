package implicits.live

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Row, SparkSession}
import scala.util.parsing.json.JSONObject


object TypeClasses2 {

  trait JsonEncoder[T] {
    def encode(entity: T): String
  }

  object JsonEncoder {

    //def apply[T](implicit ev: JsonEncoder[T]): JsonEncoder[T] = ev

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
            "name" -> entity.name.take(3).toUpperCase,
            "rate" -> entity.rate
          )
          JSONObject(data).toString()
        }
      }
  }

  implicit class JsonEncoderSyntax[T : JsonEncoder](entity: T) {
    def toJson(implicit enc: JsonEncoder[T]): String = enc.encode(entity) //with implicit parameter
    //def toJson: String = JsonEncoder[T].encode(entity) //with summoner and context bound
    //def toJson: String = implicitly[JsonEncoder[T]].encode(entity) // with implicitly and context bound
  }

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.toLevel("OFF"))

    val spark: SparkSession = SparkSession.builder
      .master("local[*]")
      .appName("TypeClasses2")
      .getOrCreate()

    import spark.implicits._

    val currencyRatesDf = currencyRates.toDF()
    val currencyRatesDs = currencyRates.toDS()

    val currencyRatesJsonDf = currencyRatesDf.map(_.toJson)
    currencyRatesJsonDf.show(truncate = false)

    val currencyRatesJsonDs = currencyRatesDs.map(_.toJson)
    currencyRatesJsonDs.show(truncate = false)
  }
}
