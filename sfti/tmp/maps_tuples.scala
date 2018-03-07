

object maps {
    
    // 默认 immutable
    val score = Map("a" -> 100, "b" -> 90, "c" -> 95)
    // 如果要使用 mutable， 需指定包名
    val s = scala.collection.mutable.Map("a" -> 100, "b" -> 90, "c" -> 95)
    
    // 空 map，需声明类型
    val empty = new scala.collection.mutable.HashMap[String, Int]
    
    val tmp = score + ("a" -> 90, "e" -> 88)  // 产生新的Map  Map(a -> 90, b -> 90, c -> 95, e -> 88)
    
    val scores = scala.collection.immutable.SortedMap("Alice" -> 10, "Fred" -> 7, "Bob" -> 3, "Cindy" -> 8)
    // scala.collection.immutable.SortedMap[String,Int] = Map(Alice -> 10, Bob -> 3, Cindy -> 8, Fred -> 7)
    
    // Java 》 Scala 
    import scala.collection.JavaConverters._
    val scores: scala.collection.mutable.Map[String, Int] = mapAsScalaMap(new java.util.TreeMap[String, Int])
    val props: scala.collection.mutable.Map[String, String] = propertiesAsScalaMap(System.getProperties)
    
    // zip
    val symbols = Array("<", "-", ">")
    val counts = Array(2, 10, 2)
    val pairs = symbols.zip(counts)  //  Array((<,2), (-,10), (>,2))
    for ((s, n) <- pairs) print(s * n)  // <<---------->>
    pairs.toMap // Map(< -> 2, - -> 10, > -> 2)
    // keys.zip(values).toMap
    
    // ex1 
    val gizmos = Map("g1" -> 100, "g2" -> 200, "g3" -> 300)
    gizmos.mapValues(_ * 0.9)
    
    // ex2 words count
    val in = new java.util.Scanner(new java.io.File("sfti/scan.txt"))
    val wc = new scala.collection.mutable.HashMap[String, Int]
    while (in.hasNext()) {
        val w = in.next()
        wc.put(w, wc.getOrElse(w, 0) + 1)
    }
    println(wc)
    
    // ex3
    val in = new java.util.Scanner(new java.io.File("sfti/scan.txt"))
    var wc = Map[String, Int]()
    while (in.hasNext()) {
        val w = in.next()
        wc = wc + (w -> (wc.getOrElse(w, 0) + 1))
    }
    println(wc)
    
    // ex4
    val in = new java.util.Scanner(new java.io.File("sfti/scan.txt"))
    var wc1 = scala.collection.immutable.SortedMap[String, Int]() withDefault (_ => 0)
    while (in.hasNext()) {
        val w = in.next()
        wc1 = wc1 + (w -> (wc1.getOrElse(w, 0) + 1))
    }
    println(wc1)
    
    // ex5
    val in = new java.util.Scanner(new java.io.File("sfti/scan.txt"))
    val wc2 = new java.util.TreeMap[String, Int]
    while (in.hasNext()) {
        val w = in.next()
        if (!wc2.containsKey(w)) wc2.put(w, 1)
        else wc2.put(w, (wc2.get(w) + 1))
    }
    println(wc2)
    
    // ex6
    import java.util.Calendar._
    val cal = scala.collection.mutable.LinkedHashMap[String, Int](
            "Monday" -> MONDAY,
            "Tuesday" -> TUESDAY,
            "Wednesday" -> WEDNESDAY
        )
    println(cal)
    
    // ex7
    val props = scala.collection.JavaConverters.mapAsScalaMap(System.getProperties)
    val maxLen = props.keySet.map(_.toString).maxBy(_.size).size
    for((k,v) <- props) println(k + " " * (maxLen - k.toString.length) + "|" + v) 
    
    // ex8
    def minmax(values: Array[Int]) = (values.min, values.max)
    
    // ex9
    def lteqgt(values: Array[Int], v: Int) = 
        (values.filter(_ < v).size, values.filter(_ == v).size, values.filter(_ > v).size)
        
    // ex10
    println("Hello".zip("World")) // Vector((H,W), (e,o), (l,r), (l,l), (o,d))
}