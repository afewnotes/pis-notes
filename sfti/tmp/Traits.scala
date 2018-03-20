
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
// mixed in
class SavingsAccount extends Account with ConsoleLogger {
  def doSomething {
    log("hi")
  }
}
