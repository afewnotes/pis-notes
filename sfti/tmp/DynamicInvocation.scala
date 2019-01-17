package tmp.Dynamic

object Dyn {

  import scala.language.dynamics
  class Person extends Dynamic {
    def selectDynamic(name: String) = {
      println(s"""selectDynamic: $name=123""")
      this
    }
    def updateDynamic(name: String)(args: Any) = {
      println(s"""updateDynamic: $name(${args})""")
      this
    }
    def applyDynamic(name: String)(args: Any*) = {
      println(s"""applyDynamic: $name(${args mkString ","})""")
      this
    }
    def applyDynamicNamed(name: String)(args: (String, Any)*) = {
      println(s"""applyDynamicNamed: $name(${args mkString ","})""")
      this
    }
  }
  object Person {
    def update(name: String)(args: Any) = println(s"""update: $name=$args""")
  }
  var person = new Person
  person.lastName // selectDynamic: lastName=123
  person.lastName = "Doe" // updateDynamic: lastName(Doe)  // selectDynamic: lastName=123
  person.find("HI", 1, 2, 3) // applyDynamic: find(HI,1,2,3)
  person.find(a = 1, b = 2, c = 3) // applyDynamicNamed: find((a,1),(b,2),(c,3))
  person.find(5) = "10" // selectDynamic: find=123 // applyDynamic: update(5,10)
}

object Exercises extends App {

  /* exercise 1 */
  3 + 4 -> 5 // (7, 5)
//  3 -> 4 + 5 // error

  /* exercise 3 分数 + - * / */
  class Fraction(_n: Int, _d: Int) {
    // 确认正负符号
    val signum = _n.signum * _d.signum
    // 分子分母除以最大公约数
    val g = gcd(_n.abs, _d.abs)
    val n = _n.abs * signum / g
    val d = _d.abs / g

    // 最大公约数
    def gcd(a: Int, b: Int): Int =
      if (b == 0) a else gcd(b, a % b)

    def +(that: Fraction) = Fraction(n * that.d + d * that.n, d * that.d)
    def -(that: Fraction) = Fraction(n * that.d - d * that.n, d * that.d)
    def *(that: Fraction) = Fraction(n * that.n, d * that.d)
    def /(that: Fraction) = Fraction(n * that.d, d * that.n)

    override def toString = s"$n/$d"
  }

  object Fraction {
    def apply(_n: Int, _d: Int) = new Fraction(_n, _d)
  }

  Fraction(-1, 3) + Fraction(5, -10) // -5/6

  /* exercise 4 Money */
  class Money(d: Int, c: Int) {
    val signum = if (d.signum < 0 || c.signum < 0) "-" else ""
    def +(that: Money) = fromCents(cents + that.cents)
    def -(that: Money) = fromCents(cents - that.cents)
    def ==(that: Money) = cents == that.cents
    def <(that: Money) = cents < that.cents

    def cents = d * 100 + c
    def fromCents(_cents: Int) = Money(_cents / 100, _cents % 100)

    override def toString = s"$signum${d.abs}.${c.abs}"
  }
  object Money {
    def apply(d: Int, c: Int) = new Money(d, c)
  }
  Money(10, 1) - Money(100, 10) // Money = -90.9
  Money(1, 2) - Money(1, 20) // Money = -0.18

  /* exercise 5 print HTML */
  class Table {
    import scala.collection.mutable.ArrayBuffer
    private val table = new ArrayBuffer[String]
    def |(t: String) = {
      table += s"<td>$t</td>"
      this // chained
    }

    def ||(t: String) = {
      table += s"</tr>\n<tr><td>$t</td>"
      this
    }

    override def toString = s"<table><tr>${table.mkString}</tr></table>"
  }

  object Table {
    def apply() = new Table()
  }
  Table() | "Java" | "Scala" || "Gosling" | "Odersky" || "JVM" | "JVM, .NET"

  /* exercise 6 ASCIIArt   */
  class ASCIIArt(val art: String) {
    def +(that: ASCIIArt) = new ASCIIArt(
      art
        .split("\n")
        .zip(that.art.split("\n"))
        .map(x => x._1 + x._2)
        .mkString("\n")
    )
    override def toString = art
  }
  object ASCIIArt {
    def apply(s: String) = new ASCIIArt(s)
  }
  val a = ASCIIArt(""" /\_/\
( ' ' )
(  -  )
 | | |
(__|__)""")

  val b = ASCIIArt("""   -----
 / Hello \
<  Scala  |
 \ Coder /
   -----""")

  /* exercise 7  */
  class BitSequence(var x: Long) {
    def apply(b: Int) = {
      println("apply invoked")
      if ((x & 1L << b % 64) > 0) 1 else 0
    }
    def update(b: Int, v: Int) = {
      println("update invoked")
      x = v match {
        case 0 => x ^ 1 << b % 64
        case 1 => x | 1 << b % 64
      }

    }

    override def toString = "%64s".format(x.toBinaryString).replace(" ", "0")
  }
  object BitSequence {
    def apply(l: Long) = new BitSequence(l)
  }
  val a1 = BitSequence(10)
  a1(10) // 0
  a1(10) = 1 // 1034
  a1(10) = 0 // 10

  /* exercise 8 */
  class Matrix(val m: Int, val n: Int) {
    private val matrix = Array.ofDim[Int](m, n)
    def fill(src: Array[Array[Int]]) = {
      for (i <- 0 until src.length)
        for (j <- 0 until src(0).length)
          matrix(i)(j) = src(i)(j)

      matrix
    }
    def apply(row: Int, col: Int) = matrix(row)(col)
    def update(row: Int, col: Int, v: Int) {
      matrix(row)(col) = v
    }

    def +(that: Matrix) = {
      // 要求规格相同
      require(m == that.m)
      require(n == that.n)

      var result = Matrix(m, n)
      for (i <- 0 until m; j <- 0 until n) {
        result(i, j) = matrix(i)(j) + that.matrix(i)(j)
      }
      result
    }

    def *(that: Matrix) = {
      // 左边列数与右边行数一致
      require(n == that.m)
      var result = Matrix(m, that.n) // 最终结果(左边行数，右边列数)
      for (i <- 0 until m; j <- 0 until that.n) {
        result(i, j) =
          (for (k <- 0 until n) yield matrix(i)(k) * that.matrix(k)(j)).sum
      }
      result
    }

    def *(s: Int) = {
      var result = Matrix(m, n)
      for (i <- 0 until m; j <- 0 until n) {
        result(i, j) = matrix(i)(j) * s
      }
      result
    }

    override def toString = matrix.map(_.mkString(" ")).mkString("\n")
  }
  object Matrix {
    def apply(m: Int, n: Int) = new Matrix(m, n)
  }

  object Test extends App {
    val right = Matrix(2, 3).fill(Array(Array(1, 2, 3), Array(4, 5, 6)))
    val left = Matrix(2, 2).fill(Array(Array(1, 2), Array(3, 4)))

//    println(left * right)
  }
  // 9 12 15
  // 19 26 33

  /* exercise 9 */
  import java.io.File
  import java.nio.file.{Path, FileSystems}
  object PathComponents {
    def unapply(path: Path): Option[(String, String)] = {
      val ap = path.toAbsolutePath
      Some((ap.getParent.toString, ap.getFileName.toString))
    }
  }
  var PathComponents(dir, name) = FileSystems.getDefault().getPath("test.txt")

  /* exercise 10 */
  import java.io.File
  import java.nio.file.{Path, FileSystems}
  object PathComponents2 {
    def unapplySeq(path: Path): Option[Seq[String]] = {
      Some(path.toAbsolutePath.toString.split(File.separatorChar).toSeq)
    }
  }
  var PathComponents2(dir2, _*) = FileSystems.getDefault().getPath("test.txt")

  /* exercise 11 */
  /* https://stackoverflow.com/questions/43343065/recursive-updatedynamic-scala-for-the-impatient-chapter-11-exercise-11 */
  import java.util.Properties
  import scala.language.dynamics
  class DynamicProps(val props: Properties) extends Dynamic {
    def updateDynamic(name: String)(value: String) = new Helper(name, props)
    def selectDynamic(name: String) =  new Helper(name, props)
  }
  class Helper(val _name: String, val p: Properties) extends Dynamic {
    def selectDynamic(name: String) = new Helper(s"${_name}.$name", p)  // chianed
    def updateDynamic(name: String)(value: String) = p.setProperty(s"${_name}.$name", value)
    override def toString = p.getProperty(_name)
  }
  val sysProps = new DynamicProps(System.getProperties)
  sysProps.username = "Fred" 
  val home = sysProps.java.home

  /* exercise 12 */
  /* https://github.com/suniala/sfti-exercises/blob/master/src/main/scala/fi/kapsi/kosmik/sfti/Chapter11.scala#L400 */
  import scala.language.dynamics
  import scala.collection.mutable

  class XMLElementSeq(private val elements: List[XMLElement]) extends Dynamic with Iterable[XMLElement] {
    override def iterator: Iterator[XMLElement] = elements.iterator

    def selectDynamic(name: String): XMLElementSeq =
      new XMLElementSeq(elements.flatMap(e => e.children.filter(c => c.label == name)))

    def applyDynamicNamed(name: String)(args: (String, String)*): XMLElementSeq = {
      val filtered = selectDynamic(name).elements.filter(e => {
        args.forall({ case (k, v) => e.attr(k) match {
          case Some(value) => value == v
          case _ => false
        }
        })
      })

      new XMLElementSeq(filtered)
    }
  }

  class XMLElement(val label: String, private val attributes: List[(String, String)]) extends Dynamic {
    val children: mutable.ListBuffer[XMLElement] = mutable.ListBuffer()

    def selectDynamic(name: String): XMLElementSeq =
      new XMLElementSeq(List(this)).selectDynamic(name)

    def attr(attrName: String): Option[String] = {
      val found = attributes.find({ case (name, _) => name == attrName })
      found match {
        case Some((_, value)) => Some(value)
        case _ => None
      }
    }

    override def toString: String = s"$label, ${attributes mkString ","}"
  }

  object XMLElement {
    def fromScalaXml(elem: scala.xml.Elem): XMLElement = {
      def rec(scalaNode: scala.xml.Node, parent: XMLElement): Unit = {
        val attributes = scalaNode.attributes.map(a => (a.key, a.value.toString)).toList
        val element = new XMLElement(scalaNode.label, attributes)

        parent.children.append(element)
        scalaNode.child.foreach(c => {
          rec(c, element)
        })
      }

      val root = new XMLElement("root", Nil)
      rec(elem, root)
      root
    }
  }

  val html = <html><body><ul></ul><ul id="42"><li>test</li></ul></body></html>
  val root = XMLElement.fromScalaXml(html)
  println(root.html.body.ul(id="42").li)

  /* exercise 13 */
  // https://github.com/suniala/sfti-exercises/blob/master/src/main/scala/fi/kapsi/kosmik/sfti/Chapter11.scala#L488
  class XMLBuilder extends Dynamic {

    def selectDynamic(label: String): XMLElement = applyDynamicNamed(label)()

    def applyDynamicNamed(name: String)(args: (String, Any)*): XMLElement = {
      val (attrArgs, otherArgs) = args.partition({ case (key, _) => !key.isEmpty })
      val (children, unNamedAttrs) = otherArgs.partition({ case (_, v) => v.isInstanceOf[XMLElement] })
      val attrs = (attrArgs ++: unNamedAttrs).map({ case (k, v) => (k, v.toString) }).toList

      // new XMLElement(name, args.toList)
      val element = new XMLElement(name, attrs)
      element.children.appendAll(children.map({ case (_, v) => v.asInstanceOf[XMLElement] }))
      element
    }
  }

  val b2 = new XMLBuilder()
  val e = b2.ul(name = "ul", b2.li(name = "inside li"), "this unName attribute")
  e //  ul, (name,ul),(,this unName attribute)
  e.children // ListBuffer(li, (name,inside li))
}
