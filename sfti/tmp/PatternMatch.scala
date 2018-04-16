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
  
}
