package implicits.live

object ImplicitConversionsProblem {

  class StringToInt(s: String) {
    def toIntWithReplace: Int = s.replace(" ", "").toInt
  }

  implicit def strToInt(str: String): StringToInt = new StringToInt(str)


  def main(args: Array[String]): Unit = {

    val a = 21
    val b = "2 1 "
    val c = "1xyz"

    val sum = a + b.toIntWithReplace + c.toIntWithReplace

    println(sum)
  }

}
