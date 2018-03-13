// 继承
class Employee(name: String, age: Int, salary: Double)
    extends Person(name, age) { // 直接调用超类构造器
  val salary = 0.0

  override def toString = s"${super.toString}[name=$name]"
}

class Person(val name: String) {
  override def toString = s"${getClass.getName}[name=${name}]"
}
class SecretPerson(codename: String) extends Person(codename) {
  override val name = "secret"
  override val toString = "secret"
}

/*
abstract class Person {
  def Id: Int // 无方法体，抽象方法
val name:String // 无初始值，抽象字段
}
class Student(val id:Int)extends Person */

/* 匿名内部类 */
object Inner {
  val alien = new Person("Fred") {
    val greeting = "Greetings"
  }

  def meet(p: Person { def greeting: String }) {
    println(s"${p.name} says: ${p.greeting}")
  }

}

/* early definition syntax */
class Creature {
  val range: Int = 10
  val env: Array[Int] = new Array[Int](range)
}
class Ant extends { override val range = 2 } with Creature

/* 预留 未实现方法 */
class NotImplemented(val n: String) {
  def d = ???
}

object implementation extends App {
  new NotImplemented("a").d
// 报错
  /* scala.NotImplementedError: an implementation is missing
  at scala.Predef$.$qmark$qmark$qmark(Predef.scala:230)
  at NotImplemented.d(<console>:12)
  ... 32 elided */
}

/* 空 */
object Empty extends App {
  def printAny(x: Any) { println(x) }
  def printUnit(x: Unit) { println(x) }
  printAny("Hello") // Hello
  printUnit("Hello") 
  // warning: a pure expression does nothing in statement position; you may be omitting necessary parentheses
  // printUnit("Hello")
  // ^
  // ()
  printUnit(())  // ()
}

/* 多参数变为元组 */
object ptt extends App {
  def show(o: Any) { println(s"${o.getClass}: $o") }
  show(3) // class java.lang.Integer: 3
  show(3, 4, 5) // class scala.Tuple3: (3,4,5)
}

/* 对象相等 */
object equal extends App {
  // 假设 description 和 price 相等则对象相等
/* 
  final override def equals(other: Any) = {
    other.isInstanceOf[Item] && {
      val that = other.asInstanceOf[Item]
      description == that.description && price == that.price
    }
  } 
  */

// 模式匹配实现
  final override def equals(other: Any) = other match {
    case that: Item => description == that.description && price == that.price
    case _ => false
  }

// hashCode
  final override def hashCode = (description, price).##
}
