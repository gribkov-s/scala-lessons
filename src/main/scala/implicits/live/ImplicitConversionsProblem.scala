package implicits.live

object ImplicitConversionsProblem {

  class StringToInt(s: String) {
    def toIntWithReplace: Int = s.replace(" ", "").toInt
  }

  implicit def strToInt(str: String): StringToInt = new StringToInt(str)


  def main(args: Array[String]): Unit = {

    val a = 42
    val b = "4 2 "
    val c = "4 2 a"

    val sum = a + b.toIntWithReplace + c.toIntWithReplace

    println(sum)
    //Exception in thread "main" java.lang.NumberFormatException: For input string: "42a"
  }

}
