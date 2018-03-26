trait Sup { def log(msg: String) }
trait A extends Sup {
  override def log(msg: String) {
    println(s"A: $msg")
  }
}
trait B extends Sup {
  override def log(msg: String) {
    println(s"B: $msg")
  }
}
class T extends A with B {
  def log2(msg: String) {
    super.log("Hi T")
  }
}
class T2 extends B with A {
  def log2(msg: String) {
    super.log("Hi T2")
  }
}
