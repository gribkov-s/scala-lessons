package implicits.live.scopes

object ImplicitScopes {


  def checkScope[T](value: T)(implicit ev: Printer[T]): Unit = ev.print(value)

  implicit val printerLocal: Printer[Number] = new Printer[Number] {
    override def print(value: Number): Unit = println(value + ": Printer from local scope")
  }

  def main(args: Array[String]): Unit = {

    val num = Number(42)
    checkScope(num)
  }
}
