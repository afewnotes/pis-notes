
trait Logger {
  def log(msg: String) // 未实现的方法默认是抽象方法
}
class ConsoleLogger extends Logger with Cloneable with Serializable {
  def log(msg: String) { println(msg) }
}

// 带具体实现的 trait
trait ConsoleLogger {
  def log(msg: String) { println(msg) }
}
// mix in
class SavingsAccount extends Account with ConsoleLogger {
  def doSomething {
    log("hi")
  }
}

// object with traits
abstract class SavingsAccount extends Account with Logger {
  def withdraw(amount: Double) {
    log("hi")
  }
}

trait ConsoleLogger extends Logger {
  def log(msg: String) { println(msg) }
}
// 在创建对象是混入一个具体的实现
val acct = new SavingsAccount with ConsoleLogger
val acct2 = new SavingsAccount with FileLogger 

// layered traits
trait X {
  def log(msg: String) {
    println("this is X")
    println(msg)
  }
}
trait A extends X {
  override def log(msg: String) {
    println("A invoked: " + msg)
    // 调用另一个 trait 的方法，具体调用哪个依据使用 trait 的顺序
    super.log(s"${java.time.Instant.now()} $msg")
  }
}

trait B extends X {
  override def log(msg: String) {
    println("B invoked: " + msg)
    super.log(
      if (msg.length <= 15) msg else s"${msg.substring(0,12)}..."
    )
  }
}
// 调用原则由远到近
val acct1 = new Object with A with B
val acct2 = new Object with B with A
acct1.log("1234567890123456")
/*
B invoked: 1234567890123456
A invoked: 123456789012...
this is X
2018-03-21T08:05:35.432Z 123456789012...
 */
acct2.log("1234567890123456")
/*
A invoked: 1234567890123456
B invoked: 2018-03-21T08:05:55.498Z 1234567890123456
this is X
2018-03-21T0...
 */


// 覆盖抽象方法
trait X {
  def log(msg: String)
}
trait A extends X {
  override def log(msg: String) {
    super.log(msg)
    // error: method log in trait X is accessed from super. It may not be abstract unless it is overridden by a member declared `abstract' and `override'
  }
}
trait B extends X {
  abstract override def log(msg: String) {
    super.log(msg) // 编译通过
  }
}

// 抽象与具体相结合
trait x {
  def a(msg: String)
  def b(msg: String) { a(s"b: ${msg}") }
  def c(msg: String) { a(s"c: $msg") }
}
class t extends x {
  override def a(msg: String){
    println(s"[custom method a] $msg")
  }
  def w {
    c("hi")
  }
}

new t b "hi" // b: hi

// 抽象字段在非抽象子类中必须初始化
trait S {
  val max: Int
  def log(msg: String) {
    println(
    if (msg.length <= max) msg
    else s"${msg.substring(0, max-3)}..."
    )
  }
}
class SA extends S {
  val max = 10
}

// 构造顺序
trait Top {
  println("Top")
}
trait A extends Top {
  println("A")
}
trait B extends Top {
  println("B")
}
class Super {
  println("Super")
}
class Sub extends Super with A with B {
  println("Sub")
}

new Sub
/* 
Super
Top
A
B
Sub
 */
