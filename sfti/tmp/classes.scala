package tmp.classes

import scala.beans.BeanProperty

// 可包含多个 class, 所有类都是 public 的
class Counter {
    private var value = 0  // 类字段必须初始化
    def increment() { if (value < Int.MaxValue) value += 1 }  // 方法默认是 public 的
    def current() = value
    // 也可定义为 def current = value ， 则使用时必须省略括号
    
    def isLess(other: Counter) = value < other.value
}
object TestCounter extends App {

  val myCounter = new Counter  // new Counter()
  myCounter.increment()  // 修改器使用括号
  println(myCounter.current)  // 访问器省略括号
}

class Message {
    val timeStamp = new java.util.Date  // 默认生成 private final 常量及对应 getter
}

// exercise 2
class BankAccount {
    private var account: Double = 0
    
    def deposit(value: Double) = {
        account += value
    }
    
    def withdraw(value: Double) = {
        if (value <= account) {
            account -= value
        } else {
            throw new Exception("no enough money..")
        }
    }
    
    val balance = account
}

// exercise 3
class Time(hrs: Int, min: Int) {
    val hours = hrs
    val minutes = min
    
    def before(other: Time): Boolean = {
        this.hours <= other.hours && this.minutes <= other.minutes
    }
}

// exercise 4
class Time2(hrs: Int, min: Int) {
    private var mins = hrs * 60 + min
    
    def before(other: Time2): Boolean = {
        mins < other.mins
    }
}

// exercise 5
class Student {
    @BeanProperty var name: String = _
    @BeanProperty var id: Long = 0
}

// exercise 6
class Person(var age: Int = 0) {
    if (age < 0) age = 0
}

// exercise 7
class Person7(name: String) {
    val firstName = name.split(" ")(0)
    val lastName = name.split(" ")(1)
}

// exercise 8
class Car(val m: String, val n: String, val y: Int = -1, var l: String = "") {
    def this(m: String, n: String, l: String) {
        this(m, n, -1, l)
    }
}
object TestCar extends App {
  new Car("a", "A")
  new Car("a", "A", 1999)
  new Car("a", "A", "asdf")
  new Car("a", "A", 1999, "asdf")
}

// exercise 10
class Employee {
    val name: String = "xx"
    val salary: Double = 0
    
    def this(name: String, salary: Double) {
        this()
        this.name = name
        this.salary = salary
    }
}
