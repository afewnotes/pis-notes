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
}
