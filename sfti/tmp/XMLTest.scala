import scala.xml.{Elem, NodeBuffer}

object XMLTest {

  def main(args: Array[String]): Unit = {
    val doc: Elem = <html><head><title>Test</title></head><body>test</body></html>
    val items: NodeBuffer = <li>item1</li><li>item2</li><li>item1</li><li>item2</li>
//    val (x, y) = (1, 2)
//    x < y
//    x <y // error
  }
}
