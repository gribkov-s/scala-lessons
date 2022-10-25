package implicits

package object live {

  case class CurrencyRateRec(date: String, code: Long, name: String, rate: Double)

  val currencyRates = Seq(
    CurrencyRateRec("2022-10-26", 36, "CNY", 8.32),
    CurrencyRateRec("2022-10-26", 840, "USD", 61.33),
    CurrencyRateRec("2022-10-26", 978, "Euro", 60.46)
  )

}
