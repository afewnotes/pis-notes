package tmp.objects

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
object Test extends App {

  import TrafficLightColor._
  def doWhat(color: TrafficLightColor) = {
    if (color == Red) "stop"
    else if (color == Yellow) "hurry"
    else "go"
  }
}
// TrafficLightColor.values 获取字段集合

// 通过 Id 或 name 查询枚举值
// TrafficLightColor(0)
// TrafficLightColor.withName("Red")


/* exercise 1 */
object Conversions {
    def inchesToCentimeters(inches: Double): Double = {
        inches * 2.54
    }
    def gallonsToLiters(gallons: Double): Double = {
        gallons * 3.78541178
    }
    def milesToKilometers(miles: Double): Double = {
        miles * 1.609344
    }
}

/* exercise 2 */
class UnitConversion(val f: Double) {
    def convert(value: Double): Double = f * value
}
object InchesToSantimeters extends UnitConversion(2.54)
object GallonsToLiters extends UnitConversion(3.78541178)
object MilesToKilometers extends UnitConversion(1.609344)

/* exercise 3 */
object Origin extends java.awt.Point
//  object 为单例， Point 类中包含public的修改方法，不安全

/* exercise 4 */
class Point private (_x: Int = 0, _y: Int = 0) 

object Point {
    def apply(_x: Int, _y: Int)  = new Point(_x, _y)
}

// println(Point(3,4))

/* exercise 5 */
object Reverse extends App {
    println(args.reverse.mkString(" "))
}

/* exercise 6 */
object Cards extends Enumeration {
    type Card = Value
    val Spade = Value("♠")
	val Club = Value("♣")
	val Heart = Value("♥")
	val Diamond = Value("♦")
	
// 	override def toString() = {
// 	    Cards.values.mkString(",")
// 	}
/* exercise 7 */
    def isRed(card: Card): Boolean = card == Heart || card == Diamond
    println(for (c <- Cards.values) yield (c, isRed(c))) 
}

// println(Cards.values)

/* exercise 8 */
object RGBCube extends Enumeration {
	val black = Value(0x000000, "Black")
	val red = Value(0xff0000, "Red")
	val green = Value(0x00ff00, "Green")
	val blue = Value(0x0000ff, "Blue")
	val yellow = Value(0xffff00, "Yellow")
	val magenta = Value(0xff00ff, "Magenta")
	val cyan = Value(0x00ffff, "Cyan")
	val white = Value(0xffffff, "White")
}



































