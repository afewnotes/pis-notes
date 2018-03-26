
class Fraction(val n: Int, val d: Int) {

}

object Fraction {
  def apply(n: Int, d: Int) = new Fraction(n, d)

  def unapply(input: Fraction): Option[(Int, Int)] = 
    if (input.d == 0) None else Some((input.n, input.d))
}

object Test extends App {
  // 直接构造
  val result = Fraction(3, 4) * Fraction(2, 5)

  // 提取器，意思是给 n 和 d 赋值，如果调用 Fraction.apply 方法，那么结果是 Fraction(3, 4)
  val Fraction(n, d) = Fraction(3, 4) // n = 3, d = 4

  case class Cur(v: Double, n: String)

  Cur(7, "USD")

  object Number {
    def unapply(input: String): Option[Int] =
      try {
        Some(input.trim.toInt)
      } catch {
        case ex: NumberFormatException => None
      }
  }

  val Number(a) = "1234" // a: Int = 1234
  val Number(a) = "asdf" // error

// unapply single or none
  object IsCompound {
    def unapply(input: String) = input.contains(" ")
  }
  case class Name(f: String, l: String)
  Name("a", "b ") match {
    case Name(f, IsCompound()) => println(f) // "b " 从 Name 中提取，然后调用 IsCompound 的 unapply
    case Name(f, l) => None
  }

  // unapplySeq
  object Name {
    def unapply(input: String): Option[Seq[String]] = None
    def unapplySeq(input: String): Option[Seq[String]] = 
      if (input.trim == "") None else Some(input.trim.split("\\s+"))
  }
  case class Name(f: String, l: String*)
  
  val author = Name("a", "b", "c")
  author match {
    case Name(f, l) => println(2)
    case Name(f, l, l1) => println(l1)
  }
}

