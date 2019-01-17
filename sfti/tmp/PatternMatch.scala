package tmp.pattern

object Patterns {

  var sign = 0
  val ch = '-'

  ch match {
    case '+' => sign = 1
    case '-' => sign = -1
    case _ => sign = 0
  }

  // 简化上面的表达式，直接赋值模式匹配
  sign = ch match {
    case '+' => 1
    case '-' => -1
    case _ => 0
  }

  // 对于多个可选项，使用 | 分隔
  // prefix match {
  //   case "0" | "0x" | "0X" ...
  //   ...
  // }

  ch match {
    case _ if Character.isDigit(ch) => digit = Character.digit(ch, 10)
    // ...
  }

  // 类型匹配
  obj match {
    case x: Int => x
    case s: String => Integer.parseInt(s)
    case _: BigInt => Int.maxValue
    case _ => 0
  }

  // 匹配数组
  arr match {
    case Array(0) => "0"
    case Array(x, y) => s"$x $y"  // 匹配长度为2的数组，并将分别绑定到 x, y
    case Array(0, rest @ _*) => rest.min // 可变参数
    case _ => "others"
  }

  // 匹配 List
  lst match {
    case 0 :: Nil => "0"
    case x :: y :: Nil => s"$x $y"
    case 0 :: tail => tail.min
    case _ => "others"
  }

  import scala.collection.JavaConversions.propertiesAsScalaMap
  for ((k, v) <- System.getProperties()) println(s"$k $v")
  // 匹配 value 为 "" 的项，其他的则被忽略
  for ((k, "") <- System.getProperties()) println(k)
  // if guard 过滤
  for ((k, v) <- System.getProperties() if v == "") println(k)

/* case class */
  abstract class Amount
  case class Dollar(value: Double) extends Amount
  case class Currency(value: Double, unit: String) extends Amount
  // 单例
  case object Nothing extends Amount

  amt match {
    case Dollar(v) => s"$$$v"  // 结果类似 $1.1，两个 $ 转义
    case Currency(_, u) => $"got $u"
    case Nothing => ""
  }

  /* nested structures */
  abstract class Item
  case class Article(description: String, price: Double) extends Item
  case class Bundle(description: String, discount: Double, items: Item*) extends Item

  def price(it: Item): Double = it match {
    case Article(_, p) => p
    case Bundle(_, disc, its @ _*) => its.map(price _).sum - disc
  }

  val bundle = Bundle("bundle1", 20.0,
    Article("a", 19.9),
    Bundle("bundle2", 10.0,
      Article("c", 17.7)))

  bundle match {
    case Bundle(_, _, Article(desc, _), _*) => println(desc)
  }

/* sealed */
  sealed abstract class TrafficLightColor
  case object Red extends TrafficLightColor
  case object Yellow extends TrafficLightColor
  case object Green extends TrafficLightColor

  color match {
    case Red => "stop"
    case Yellow => "hurry"
    case Green => "go"
  }

  /* partial function */
  val f: PartialFunction[Char, Int] = { case '+' => 1; case '-' => -1 }
  f('-') // -1
  f.isDefinedAt('0') // false
  f('0') // 报错
}

object Exercises {
  /* 2 */
  def swap(src: (Int, Int)): (Int, Int) = src match {
    case (a, b) => (b, a)
    case _ => None
  }

  /* 3 */
  def swap(arr: Array[Int]): Array[Int] = arr match {
    case Array(f, s) => {
      arr(0) = s
      arr(1) = f
      arr
    }
    case _ => arr
  }

  /* 4 */
  abstract class Item
  case class Article(description: String, price: Double) extends Item
  case class Bundle(description: String, discount: Double, items: Item*) extends Item
  case class Multiple(num: Int, item: Item) extends Item

  def price(it: Item): Double = it match {
    case Article(_, p) => p
    case Bundle(_, disc, its @ _*) => its.map(price _).sum - disc
    case Multiple(num, it) => num * price(it)
  }

  /* 5 */
  def leafSum(tree: List[Any]): Int = {
    (0 /: tree){(result: Int, node) => node match {
      case n: Int => result + n
      case lst: List[_] => result + leafSum(lst)
      case _ => result
    }}
  }
  leafSum(List(1, List(2,3), List(4,5, List(1,2,3)))) // 21

  /* 6 */
  sealed abstract class BinaryTree
  case class Leaf(value: Int) extends BinaryTree
  case class Node(left: BinaryTree, right: BinaryTree) extends BinaryTree

  def leafSum(tree: BinaryTree): Int = tree match {
    case Leaf(value) => value
    case Node(left, right) => leafSum(left) + leafSum(right)
  }

  /* 7 */
  case class Node(nodes: BinaryTree*) extends BinaryTree

  def leafSum(tree: BinaryTree): Int = tree match {
    case Leaf(value) => value
    // case node: Node => (0 /: node.nodes)((result, n) => result + leafSum(n))
    case Node(rest @ _*) => rest.map(leafSum(_)).sum
  }

  /* 8 */
  case class OpNode(op: Char, nodes: BinaryTree*) extends BinaryTree

  def leafSum(tree: BinaryTree): Int = tree match {
    case Leaf(value) => value
    case OpNode('+', nodes @ _*) => nodes.map(leafSum(_)).sum
    case OpNode('-', Leaf(value)) => 0 - value
    case OpNode('-', nodes @ _*) => nodes.map(leafSum(_)).reduceLeft((p, c) => p - c)
    case OpNode('*', nodes @ _*) => nodes.map(leafSum(_)).product
  }

  leafSum(OpNode('+', OpNode('*', Leaf(3), Leaf(8)), Leaf(2), OpNode('-', Leaf(5)))) // 21
  leafSum(OpNode('+', OpNode('*', Leaf(3), Leaf(8)), Leaf(2), OpNode('-', Leaf(5), Leaf(3)))) // 28

  /* 9 */
  def sum(lst: List[Option[Int]]): Int = (for (Some(i) <- lst) yield i).sum  // 这也是模式匹配……
  def sum(lst: List[Option[Int]]): Int = lst.map(_.getOrElse(0)).sum

  sum(List(Some(1), Some(2), None)) // 3

  /* 10 */
  def f(x: Double) = if (x != 1) Some(1 / (x - 1)) else None
  def g(x: Double) = if (x >= 0) Some(x) else None
  def compose(f1: Double => Option[Double], f2: Double => Option[Double]): Double => Option[Double] = 
    (d: Double) => f2(d) match {
      case Some(v) => f1(v)
      case None => None
    }
}
