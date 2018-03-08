class Accounts {
    val id = Accounts.newUniqueNumber()
    private var balance = 0.0
    def deposit(amount: Double) {
        balance += amount
    }
}


// singleton 
// Accounts 类的伴生对象
object Accounts {
    println("invoked") // 构造器(包括构造器内的语句)只在第一次被调用时执行
    
    private var lastNumber = 0
    def newUniqueNumber() = {
        lastNumber += 1;
        lastNumber
    }
}
/*
scala> Accounts.newUniqueNumber()
invoked
res0: Int = 1

scala> Accounts.newUniqueNumber()
res1: Int = 2

*/

// 默认值
abstract class UndoableAction(val description: String) {
    def undo(): Unit
    def redo(): Unit
}

object DoNothingAction extends UndoableAction("Do nothing") {
    override def undo() {}
    override def redo() {}
}
// val actions = Map("open" -> DoNothingAction, "save" -> DoNothingAction, ...)


/* apply 方法 */
class Account private (val id: Int, initialBalance: Double) {
    private var balance = initialBalance
}

object Account {
    def apply(initialBalance: Double) =
        new Account(newUniqueNumber(), initialBalance)
    
    private var lastNumber = 0
    def newUniqueNumber() = {
        lastNumber += 1;
        lastNumber
    }
}
// 使用
// val acc = Account(10000.0)

/* App 对象*/
// scala -Dscala.time Hello
object Hello extends App {
    println("Hello")
}

/* 枚举 */
object TrafficLightColor extends Enumeration {
    val Red, Yellow, Green = Value // Value 为内部类
    // 等价于
    // val Red = Value
    // val Yellow = Value
    // val Green = Value
    
    // 指定 ID，name
    // val Red = Value(0, "STOP")
    // val Yellow = Value(10)  // name="Yellow"
    // val Green = Value("GO") // ID=11
    
    // 定义类型别名，方便使用
    type TrafficLightColor = Value
}

import TrafficLightColor._
def doWhat(color: TrafficLightColor) = {
    if (color == Red) "stop"
    else if (color == Yellow) "hurry"
    else "go"
}
// TrafficLightColor.values 获取字段集合

// 通过 Id 或 name 查询枚举值
// TrafficLightColor(0)
// TrafficLightColor.withName("Red")















































