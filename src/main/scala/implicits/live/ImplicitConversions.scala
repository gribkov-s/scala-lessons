package implicits.live

import org.apache.avro.Schema
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.avro.SchemaConverters


object ImplicitConversions {

  class DfAvroSchema(df: DataFrame) {
    def getAvroSchema(nameSpace: String, recName: String): Schema =
      SchemaConverters.toAvroType(df.schema, recordName = recName, nameSpace = nameSpace)
  }

  implicit def dfToDfAvroSchema(df: DataFrame): DfAvroSchema = new DfAvroSchema(df)


  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.toLevel("OFF"))

    val spark: SparkSession = SparkSession.builder
      .master("local[*]")
      .appName("ImplicitConversions")
      .getOrCreate()

    import spark.implicits._

    val currencyRatesDf = currencyRates.toDF()

    val avroSchema =
      currencyRatesDf.getAvroSchema("currency", "currency_rates_rec")

    println(avroSchema.toString(true))

  }
}
