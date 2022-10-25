package implicits.live

package object scopes {

  case class Number(value: Int)

  /*implicit val printerPackage: Printer[Number] = new Printer[Number] {
    override def print(value: Number): Unit = println(value + ": Printer from package scope")
  }*/

}
