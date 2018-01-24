

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
}