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

  // Control Abstraction
  def runInThread(block: () => Unit) {
    new Thread {
      override def run() { block() }
    }.start()
  }
  // 调用时，需要使用 ()=>
  runInThread { () => println("hi"); Thread.sleep(1000); println("bye")}
  // 也可以使用 传名调用 ，省略括号
  def runInThread(block: => Unit) {
    new Thread {
      override def run() { block } // 也省略括号
    }.start()
  }
  // 调用时更加简洁
  runInThread { println("hi"); Thread.sleep(1000); println("bye") }

  def until(condition: => Boolean)(block: => Unit) {
    if (!condition) {
      block
      until(condition)(block)
    }
  }
  var x = 10
  until(x == 0) {
    x -= 1
    println(x)
  }

  def indexOf(str: String, ch: Char): Int = {
    var i = 0
    until (i == str.length) {
      if (str(i) == ch) return i
      i += 1
    }
    return -1
  }
}

object Exercises {

  /* exercise 1 */
  def values(fun: Int => Int, low: Int, high: Int) = 
    for (i <- low to high) yield (i, fun(i))

  /* exercise 2 */
  Array(1,30,9,12,0).reduceLeft((a,b) => if (a > b) a else b) // 30

  /* exercise 3 */
  def factorial(i: Int) = i match {
    case 0 => 1
    case _ => (1 to i).reduceLeft(_ * _)
  }

  /* exercise 4 */
  def factorial(i: Int) = (1 to i).foldLeft(1)(_ * _)

  /* exercise 5 */
  def largest(fun: Int => Int, inputs: Seq[Int]) = 
    inputs.map(fun).reduceLeft((a, b) => if (a > b) a else b)

  largest(x => 10 * x - x * x, 1 to 10) // 25

  /* exercise 6 */
  def largestAt(fun: Int => Int, inputs: Seq[Int]) =
    inputs.map(i => (i, fun(i))).reduceLeft((a, b) => if (a._2 > b._2) a else b)._1

  largestAt(x => 10 * x - x * x, 1 to 10) // 5

  /* exercise 7 */
  def adjustToPair(fun: (Int, Int) => Int)(inputs: (Int, Int)) = 
    fun(inputs._1, inputs._2)

  val pairs = (1 to 10) zip (11 to 20)
  pairs.map(adjustToPair(_ + _))

  /* exercise 8 */
  def checkLength(strs: Array[String], ints: Array[Int]) =
    strs.corresponds(ints)(_.length == _)

  /* exercise 10 */
  def unless(condition: Boolean)(block: => Unit) = // 第一个参数不需要传名参数，只会使用一次；需要使用柯里化，看起来更像条件控制语句
    if (!condition) block
}
