package tmp.inher

// 继承
class Employee(name: String, age: Int, salary: Double)
    extends Person(name) { // 直接调用超类构造器
//  val salary = 0.0

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
  printUnit(()) // ()
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
    case _          => false
  }

// hashCode
  final override def hashCode = (description, price).##
}

/* Value Classes */
class MilTime(val time: Int) extends AnyVal {
  def minutes = time % 100
  def hours = time / 100
  override def toString = time
}

object MilTime {
  def apply(t: Int) =
    if (0 <= t && t <= 2400 && t % 100 < 60) new MilTime(t)
    else throw new IllegalArgumentException
}

object TestValue extends App {
  val a = new MilTime(1230)
  // 不会创建新对象，而是直接使用值 1230；
  println(a.hours)
  println(a.minutes)
  // 且 a 无法调用 Int 的方法
  // a * 100 // 报错
}

/* exercise 1 */
class BankAccount(initialBalance: Double) {
  private var balance = initialBalance
  def currentBalance = balance
  def deposit(amount: Double) = {
    balance += amount
    balance
  }
  def withdraw(amount: Double) = {
    balance -= amount
    balance
  }
}

class CheckingAccount(initialBalance: Double)
    extends BankAccount(initialBalance) {
  def charges { balance -= 1 }
  override def deposit(amount: Double) = {
    charges
    super.deposit(amount)
  }
  override def withdraw(amount: Double) = {
    charges
    super.withdraw(amount)
  }
}

/* exercise 2 */
class SavingsAccount(initialBalance: Double)
    extends BankAccount(initialBalance) {
  private var free = 3
  def charges {
    free -= 1
    if (free == 0) balance -= 1
  }
  def earnMonthlyInterest(rate: Double) = {
    // 每月利息，3次操作免费
    balance += balance * rate
    free = 3
  }
  override def deposit(amount: Double) = {
    charges
    super.deposit(amount)
  }
  override def withdraw(amount: Double) = {
    charges
    super.withdraw(amount)
  }
}

/* exercise 3 */
class Bicycle(cadence: Int, gear: Int, speed: Int) {
  def applyBrake(decrement: Int) = {
    speed -= decrement
  }
  def speedUp(increment: Int) = {
    speed += increment
  }
}
class MountainBike(cadence: Int, gear: Int, speed: Int, seatHeight: Int)
    extends Bicycle(cadence, gear, speed) {
  def setHeight(height: Int) {
    seatHeight = height
  }
}

/* exercise 4 */
abstract class Item {
  def price: Double
  def description: String
}
class SimpleItem(val price: Double, val description: String) extends Item
class Bundle extends Item {
  private var items: List[Item] = List()
  def add(item: Item) = { items = item :: items }
  def price: Double = items.map(_.price).sum
  def description: String = items.map(_.description).mkString(", ")
}

/* exercise 5 */
class Point(x: Double, y: Double)
class LabeledPoint(label: String, x: Double, y: Double) extends Point(x, y)

/* exercise 6 */
abstract class Shape {
  def centerPoint: Point
}
class Rectangle(leftTop: Point, rightBottom: Point) extends Shape {
  def centerPoint = new Point((leftTop.x + rightBottom.x) / 2, (leftTop.y + rightBottom.y) / 2)
}
class Circle(val centerPoint: Point, radius: Int) extends Shape


/* exercise 7 */
class Square(x: Int, y: Int, width: Int) extends java.awt.Rectangle(x,y,width,width) {
  def this() = this(0, 0, 0)
  def this(width: Int) = this(0, 0, width)
}
