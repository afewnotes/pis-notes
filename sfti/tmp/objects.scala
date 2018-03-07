
// singleton
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