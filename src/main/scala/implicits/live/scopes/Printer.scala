package implicits.live.scopes

trait Printer[T] extends Printable with Extendable {
  def print(value: T): Unit
}

object Printer {
  /*implicit val printerObj: Printer[Number] = new Printer[Number] {
    override def print(value: Number): Unit = println(value + ": Printer from Printer companion object scope")
  }*/
}
