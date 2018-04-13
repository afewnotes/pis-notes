object Collections {
  def multi(l: List[Int]): Int = l match {
    case Nil    => 1
    case h :: t => h * multi(t)
  }

  val str = 1 #:: 2 #:: 3 #:: Stream.empty
  // Stream(1, ?) 只打印了 head，因为 tail 还未计算

  def fibFrom(a: Int, b: Int): Stream[Int] = a #:: fibFrom(b, a + b)
  fibFrom(1, 1).take(7) // 此处还未计算
    .toList // 开始计算 List(1, 1, 2, 3, 5, 8, 13)
  fibFrom(1, 1).take(7).force // 与上面等效

  val mixedList = List("a", 1, 2, "b", 19, 42.0) //  List[Any]
  val results = mixedList collect {
     case s: String => "String:" + s
     case i: Int => "Int:" + i.toString
  } // List(String:a, Int:1, Int:2, String:b, Int:19)

  "-3+4".collect { case '+' => 1; case '-' => -1 } //  Vector(-1, 1)

  val words = "Hello he World wo".split(" ")
  val map = words.groupBy(_.substring(0, 1).toUpperCase) // Map(W -> Array(World, wo), H -> Array(Hello, he))

  val l = List(1, 7, 2, 9)
  l.reduceLeft(_ - _) // (((1 - 7) - 2) - 9) = -17
  l.reduceRight(_ - _) // (1 - (7 - (2 - 9))) = -13

  l.foldLeft(0)(_ - _) // ((((0 - 1) - 7) - 2) - 9) = -19 // 上次计算结果在左侧，即第一个参数
  l.foldRight(0)(_ - _) // (1 - (7 - (2 - (9 - 0)))) = -13 // 上次计算结果在右侧，即第二个参数

  (1 to 10).scanLeft(0)(_ + _) // Vector(0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55)

  List(1,2,3) zip List("a","b","c") // List((1,a), (2,b), (3,c))

  List(1, 2, 3).zipAll(List(11, 12), 0, 10) // List((1,11), (2,12), (3,10)) //0 表示左侧默认值，10 表示右侧默认值

  List(1, 2, 3).zipWithIndex // List((1,0), (2,1), (3,2))

  val palindromicSquares = (1 to 1000000).view
    .map(x => x * x)
    .filter(x => x.toString == x.toString.reverse)

  palindromicSquares
    .take(10)
    .mkString(",") // 1,4,9,121,484,676,10201,12321,14641,40804

  for (i <- (0 until 100).par) print(s" $i") // 并行化集合打印

  (for (i <- (0 until 100).par) yield i) == (0 until 100)  // yield 产生的结果与串行的结果一致
}


object Exercises {

  /* 1 */

  def indexes(s: String) = {
    import scala.collection.mutable.{HashMap, LinkedHashSet}
    val result = new HashMap[Char, LinkedHashSet[Int]]

    // zipWithIndex 将每个字符处理为带下表的元组
    for ((char, index) <- s.zipWithIndex) {
      result.getOrElseUpdate(char, new LinkedHashSet[Int]) += index
    }

    result
  }

  indexes("Mississippi") // Map(M -> Set(0), s -> Set(2, 3, 5, 6), p -> Set(8, 9), i -> Set(1, 4, 7, 10))

  /* 2 */
  def indexes(s: String) = {
    s.zipWithIndex.groupBy(_._1).map(x => (x._1, x._2.map(_._2).toList))
  }
  indexes("Mississippi") // Map(M -> List(0), s -> List(2, 3, 5, 6), p -> List(8, 9), i -> List(1, 4, 7, 10))

  /* 3 */
  import scala.collection.mutable.ListBuffer
  def removeEven(l: ListBuffer[String]) = {
    for (i <- (l.length - 1) to 0 by -2) {
      l.remove(i)
    }
    l
  }

  def removeEven(l: ListBuffer[String]) = l.filter(l.indexOf(_) % 2 == 1)

  removeEven(ListBuffer("a", "b", "c", "d")) // ListBuffer(b, d)

  /* 4 */
  def getValues(keys: Traversable[String], map: Map[String, Int]) = {
    keys.flatMap(map.get) // 每个 key 作为 map.get 的参数
    // 如果用 keys.map ，get 的返回值为 Option
  }
  getValues(List("a", "b"), Map("a" -> 1, "b" -> 2, "c" -> 3)) // List(1, 2)

  /* 5 */
  def makeString[T](xs: Traversable[T], s: Char = ','): String = {
    if (xs.isEmpty) ""
    // else xs.reduceLeft((a: Any, b: T) => a + ", " + b).toString
    else xs.map(_.toString).reduceLeft(_ + s + _)
  }

  makeString(Array(1, 2, 3)) //  1,2,3
  makeString(Array(1, 2, 3), ' ') //  1 2 3

  /* 6 */
  // (List[Int]() /: lst)(_ :+ _) // foldLeft
  // (lst :\ List[Int]())(_ :: _) // foldRight
  // 反转
  def reverse(lst: List[Int]) =
    (lst :\ List[Int]())((pre, result) => result :+ pre) // 相当于尾部追加，效率低   -> List(), List(1), List(2, 1), List(3, 2, 1)
  def reverse(lst: List[Int]) =
    (List[Int]() /: lst)((result, pre) => pre :: result) // 更高效，只添加 head 元素 <- List(3, 2, 1), List(3, 2), List(3), List()

  reverse(List(1,2,3)) // List(3, 2, 1)

  /* 7 */
  def multiply(prices: Iterable[Int], quantities: Iterable[Int]): Iterable[Int] = {
    (prices zip quantities) map Function.tupled { _ * _ }
  }
  val prices = List(5, 20, 9)
  val quantities = List(10, 2, 1)
  multiply(prices, quantities) // List(50, 40, 9)

  /* 8 */
  def splitArray(arr: Array[Double], col: Int) = {
    assert(arr.length % col == 0, "can't split even")

    arr.grouped(arr.length / col).toArray
  }
  splitArray(Array(1, 2, 3, 4, 5, 6, 7, 8), 2) //  Array(Array(1.0, 2.0, 3.0, 4.0), Array(5.0, 6.0, 7.0, 8.0))

  /* 9 */

  // for (i <- 1 to 5; j <- 1 to i) yield i * j 等价于
  (1 to 5).flatMap(i => (1 to i).map(j => i * j))
  // Vector(1, 2, 4, 3, 6, 9, 4, 8, 12, 16, 5, 10, 15, 20, 25)

  (1 to 5).map(i => (1 to i).map(j => i * j)) // 比较 map 与 flatMap 结果
  // Vector(Vector(1), Vector(2, 4), Vector(3, 6, 9), Vector(4, 8, 12, 16), Vector(5, 10, 15, 20, 25))

  /* 10 */
  java.util.TimeZone.getAvailableIDs
    .groupBy(_.split("/")(0))
    .map(a => (a._2.length, a._1))
    .max // (165,America)

  /* 11 */
  def freq(str: String) = {
    // val frequencies = new scala.collection.mutable.HashMap[Char, Int]
    // for (c <- str.par) frequencies(c) = frequencies.getOrElse(c, 0) + 1
    // frequencies
    import scala.collection.immutable.HashMap
    str.par.aggregate(HashMap[Char, Int]())(
      (m, c) => m + (c -> (m.getOrElse(c,0) + 1)), 
      // (a, b) => a ++ b.map{ case (k,v) => k -> (v + a.getOrElse(k, 0)) }  // 通过 b 覆盖 a 中记录实现相加
      (a, b) => (a /: b)((m, e) => m + (e._1 -> (m.getOrElse(e._1, 0) + e._2)))   // foldLeft 遍历，实现 a + b
    )
  }
  freq("abbccc") // Map(a -> 1, b -> 2, c -> 3)
}
