
// 可包含多个 class, 所有类都是 public 的
class Counter {
    private var value = 0  // 类字段必须初始化
    def increment() { value += 1 }  // 方法默认是 public 的
    def current() = value
    // 也可定义为 def current = value ， 则使用时必须省略括号
    
    def isLess(other: Counter) = value < other.value
}

val myCounter = new Counter  // new Counter()
myCounter.increment()  // 修改器使用括号
println(myCounter.current)  // 访问器省略括号

class Message {
    val timeStamp = new java.util.Date  // 默认生成 private final 常量及对应 getter
}