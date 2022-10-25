package implicits.live.scopes

trait Printable

object Printable {
  implicit val printerSuperObj: Printer[Number] = new Printer[Number] {
    override def print(value: Number): Unit = println(value + ": Printer from Printable companion object scope")
  }
}
