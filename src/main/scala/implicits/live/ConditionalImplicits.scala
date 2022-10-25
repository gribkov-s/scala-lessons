package implicits.live

import org.apache.avro.Schema
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import com.hortonworks.registries.schemaregistry.client.SchemaRegistryClient
import scala.jdk.CollectionConverters.MapHasAsJava
import org.apache.spark.sql.avro.SchemaConverters


object ConditionalImplicits {

  class DfAvroSchema(df: DataFrame) {
    def getAvroSchema(nameSpace: String, recName: String): Schema =
      SchemaConverters.toAvroType(df.schema, recordName = recName, nameSpace = nameSpace)
  }

  implicit def dfToDfAvroSchema(df: DataFrame)
                               (implicit client: SchemaRegistryClient): DfAvroSchema = new DfAvroSchema(df)


  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.toLevel("OFF"))

    val spark: SparkSession = SparkSession.builder
      .master("local[*]")
      .appName("ConditionalImplicits")
      .getOrCreate()

    import spark.implicits._

    val currencyRatesDf = currencyRates.toDF()

    val clientConfig = Map("schema.registry.url" -> "http://my-schema-registry:8080")
    implicit val client: SchemaRegistryClient = new SchemaRegistryClient(clientConfig.asJava)

    val avroSchema =
      currencyRatesDf.getAvroSchema("currency", "currency_rates_rec")

    println(avroSchema.toString(true))
  }
}
