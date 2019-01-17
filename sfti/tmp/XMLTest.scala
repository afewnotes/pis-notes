import scala.xml.{Elem, NodeBuffer}

object XMLTest {

  def main(args: Array[String]): Unit = {
    val doc: Elem = <html><head><title>Test</title></head><body>test</body></html>
    val items: NodeBuffer = <li>item1</li><li>item2</li><li>item1</li><li>item2</li>
    // 注意 < > 的使用，前后添加空格
//    val (x, y) = (1, 2)
//    x < y
//    x <y // error
    val elem = <a href="http://scala-lang.org">The <em>Scala</em> language</a>
    println(elem.label)
    println(elem.attributes)

  }
}
