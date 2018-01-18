class test {
    def m1(x:Int) = x+3
    val f1 = (x:Int) => x+3
}

object impatient {
    def signum(n: Int): Int = {
        if (n > 0) 1 else -1
    }
    
    def apply(n: Int): Int = {
        signum(n)
    }
    
    def main(args: Array[String]) {
    //  for (i <- 0 to 10 reverse) println(i)
        countDown(10)
    }
    
    def countDown(n: Int) {
        for(i <- 0 to n reverse) println(i)
    }
    
    def product(s: String): Int = {
        (for (c <- s) c.toInt).product
        
        // s.map(_.toInt).product
        
        // s.head.toInt * product(s.tail)
    }
    
    def productR(s: String):Int = s match {
      case "" => 1
      case _ => s.head.toInt * productR(s.tail)
    }
    
    def compute(x: Int, n: Int): Int = {
        if (n == 0) 1
        else if (n < 0) 1 / compute(x, -n)
        else if (n % 2 == 0) power(compute(x, n / 2), 2).toInt
        else x * compute(x, n - 1)
    }
    
    // 固定长度
    val nums = new Array[Int](10)  // nums: Array[Int] = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    val a = new Array[String](10)   // a: Array[String] = Array(null, null, null, null, null, null, null, null, null, null)
    val s = Array("Hello", "World") // s: Array[String] = Array(Hello, World)
    s(0) = "Go" // Array(Go, World)
    
    // 可变长度
    import scala.collection.mutable.ArrayBuffer
    val b = ArrayBuffer[Int]()  // b: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer()
    b += 1  // res: b.type = ArrayBuffer(1)
    b += (2,3,4,5)  // res: b.type = ArrayBuffer(1, 2, 3, 4, 5)
    b ++= Array(6,7,8)  // res: b.type = ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8)
    b.trimEnd(5)    // scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 2, 3)
    b.insert(2, 10) // scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 2, 10, 3)
    b.insert(2, 11,12,13)   // scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 2, 11, 12, 13, 10, 3)
    b.remove(2) // scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 2, 12, 13, 10, 3)
    b.remove(2, 3) // scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(1, 2, 3)
    val a = b.toArray   // Array[Int] = Array(1, 2, 3)
    a.toBuffer  // scala.collection.mutable.Buffer[Int] = ArrayBuffer(1, 2, 3)
    a.sum   // 6
    a.min   // 1
    a.max   // 3
    Array(1,10,2,9,8).sortWith(_ > _) // ArrayBuffer(10, 9, 8, 2, 1)
    Array("asd","sdf","ss").sorted  // Array(asd, sdf, ss)
    val a = Array(1,3,9,2,11)
    scala.util.Sorting.quickSort(a) // Array(1, 2, 3, 9, 11)
    // for 推导
    val x = Array(1,2,3)
    val result = for (e <- x) yield 2 * e // Array(2,4,6)
    for (e <- x if e % 2 == 0) yield 2 * e
    x.filter(_ % 2 == 0).map(_ * 2)
    x filter { _ % 2 == 0 } map { _ * 2 }
    
    // 删除所有负数（第一个负数除外）
    var first = true 
    var n = first.length
    var i = 0
    while (i < n) {
        if (a(i) >= 0) i += 1
        else {
            if (first) {
                first = false
                i += 1
            } else {
                a.remove(i)
                n -= 1
            }
        }
    }
    // for / yield 写法
    val indexes = for (i <- 0 until a.length if a(i) < 0) yield i
    // 从后向前删除更高效，因为每次 remove 都需要将元素向前位移/shift，从后删除 shift 的元素数量更少
    for (j <- (1 until indexes.length).reverse) a.remove(indexes(j))
    
    
}