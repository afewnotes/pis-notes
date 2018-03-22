
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

// 初始化字段
// trait 无构造参数的限制
// 例如想为日志自定义文件名称
val acct = new SavingsAccount with FileLogger("myapp.log") // 发生编译错误
///* 使用抽象字段 */
trait FileLogger extends Logger {
  val filename: String
  val out = new PrintStream(filename) // 此处会发生异常
  def log(msg: String) { out.println(msg); out.flush() }
}
val acct = new SavingsAccount with FileLogger {
  val filename = "myapp.log" // 不起作用
}
// new 关键字创建一个匿名类的实例来继承 SavingsAccount 和 FileLogger
// filename 初始化发生在匿名类(即子类)中，而子类构造器最后才会被调用
// 在此之前的 FileLogger 已经出发构造，out 无法实例化

// /* 使用 early definition */
val acct = new {
  val filename = "myapp.log"  // 在构造器之前执行
} with SavingsAccount with FileLogger

// 也可以 lazy 定义， 只在需要使用时进行初始化，但效率较低，后续每次使用都会判断是否已初始化过
trait FileLogger extends Logger {
  val filename: String
  lazy val out = new PrintStream(filename)
  def log(msg: String) { out.println(msg) }
}

/* trait 继承 class */
trait LoggerException extends Exception with ConsoleLogger {
  def log() { log(getMessage()) }
}
class UnhappyException extends LoggerException {
  override def getMessage() = "ar"
}
class UnhappyException extends IOException with LoggerException // OK
class UnhappyException extends Test with LoggerException // Error: Unrelated superclasses

/* self type  */
trait LoggerException extends ConsoleLogger {
  this: Exception =>  // 可不继承 Exception，混入改 trait 的类需要是 Exception 类型/子类
    def log() { log(getMessage()) } // 调用 Exception 的 getMessage()
}
val f = new Test with LoggerException // Error
// 使用结构类型
trait LoggerException extends ConsoleLogger {
  this: { def getMessage(): String } =>  // 混入改 trait 的类需要包含 getMessage 方法
    def log() { log(getMessage()) }
}
