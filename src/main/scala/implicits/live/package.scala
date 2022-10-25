package implicits

package object live {

  case class CurrencyRateRec(date: String, code: Long, name: String, rate: Double)

  val currencyRates = Seq(
    CurrencyRateRec("2022-10-17", 36, "AUD", 39.04),
    CurrencyRateRec("2022-10-17", 840, "USD", 62.10),
    CurrencyRateRec("2022-10-17", 978, "eur", 59.94)
  )

}
