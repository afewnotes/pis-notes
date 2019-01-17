trait Sup { def log(msg: String) }
trait A extends Sup2 {
  override def log(msg: String) {
    println(s"A: $msg")
  }
}
trait B extends Sup2 {
  override def log(msg: String) {
    println(s"B: $msg")
  }
}
class T extends A2 with B2 {
  def log2(msg: String) {
    super.log("Hi T")
  }
}
class T2 extends B2 with A2 {
  def log2(msg: String) {
    super.log("Hi T2")
  }
}
