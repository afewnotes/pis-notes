object Functions {

  import scala.math._
  val num = 1.2
  val fun = ceil _ // fun: Double => Double

  val f: Double => Double = ceil
  // 直接调用
  f(1.1)
  // 传递函数
  Array(1,3.14,1.0).map(f)

  // 定义参数类型 或 指定函数类型
  val fs = (_: String).charAt(_: Int)
  val fs: (String, Int) => Char = _.charAt(_)

  // 匿名函数
  Array(1,2,3).map( (x: Int) => 3 * x)
  Array(1,2,3).map { (x: Int) => 3 * x } // 使用花括号代替括号
  Array(1,2,3) map { (x: Int) => 3 * x } // 一般在中缀形式调用

  def valueAtOneQuarter(f: (Double) => Double) = f(0.25)
  valueAtOneQuarter(ceil _) // 1
  valueAtOneQuarter(sqrt _) // 0.5

  def mulBy(factor: Double) = (x: Double) => factor * x
  val quintuple = mulBy(5) // (x: Double) => 5 * x
  quintuple(20) // 100

  // 参数类型推导
  valueAtOneQuarter((x: Double) => 3 * x)
  valueAtOneQuarter((x) => 3 * x) // 省略参数类型
  valueAtOneQuarter(x => 3 * x) // 一个参数可省略括号
  valueAtOneQuarter(3 * _) // 右侧只使用到一次，用 _ 代替

  (1 to 9).map("*" * _).foreach(println _)
  (1 to 9).filter(_ % 2 == 0)
  (1 to 9).reduceLeft(_ * _)
  "mary had a little lamb".split(" ").sortWith(_.length < _.length)

  var counter = 0
  val button = new JButton("Increment")
  button.addActionListener(new ActionListener {
    override def actionPerformed(event: ActionEvent) {
      counter += 1
    }
  })
  // SAM
  button.addActionListener(event => counter +=1) // 函数字面量起作用

  val listener = (event: ActionListener) => println(counter)
  button.addActionListener(listener) // 无法转换为 SAM 接口

  val listener: ActionListener = event => println(counter) // 通过变量接收函数作为 SAM 接口
  button.addActionListener(listener) // OK

  // Currying
  val mul = (x: Int, y: Int) => x * y
  val mulOnAtATime = (x: Int) => ((y: Int) => x * y)
  // def 方式定义
  def mulOnAtATime(x: Int)(y: Int) = x * y
  // 柯里化配合类型推导
  val a = Array("h", "w")
  val b = Array("H", "W")
  a.corresponds(b)(_.equalsIgnoreCase(_))
}
