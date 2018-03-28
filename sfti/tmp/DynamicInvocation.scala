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
  3 -> 4 + 5 // error

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
  val a = BitSequence(10)
  a(10) // 0
  a(10) = 1 // 1034
  a(10) = 0 // 10

  
}
