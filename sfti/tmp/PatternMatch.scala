object Patterns {

  var sign = 0
  val ch = '-'

  ch match = {
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
  case class Currency(value: Double, unit: String) extens Amount
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
