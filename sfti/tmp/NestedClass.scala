import scala.collection.mutable.ArrayBuffer

class Network {
    class Member(val name: String) {
        val contacts = new ArrayBuffer[Member]
    }
    
    private val members = new ArrayBuffer[Member]
    
    def join(name: String)= {
        val m = new Member(name)
        members += m
        m
    }
}

object TestNested {
  // chartter.Member 不同于 myFace.Member
  val chatter = new Network
  val myFace = new Network

  val fred = chatter.join("Fred")
  val wilma = chatter.join("Wilma")
  fred.contacts += wilma  // 类型均为 chatter.Member

  val barney = myFace.join("Barney") // 类型为 myFace.Member
//  fred.contacts += barney  // 报错，类型不同无法添加
}

// 如果想内部类通用
// 1. 将内部类放到伴生对象
object Test {
    class Inner(val n: String) {
//        val c = new ArrayBuffer[Member]
    }
}

//class Test {
//    private val m = new ArrayBuffer[Network.Member]
//}
// 2. 使用类型投射(type projection)
class Test {
    class Inner(val n: String) {
        val c = new ArrayBuffer[Network#Member]
    }
}
