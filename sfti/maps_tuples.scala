

object maps {
    
    // 默认 immutable
    val score = Map("a" -> 100, "b" -> 90, "c" -> 95)
    // 如果要使用 mutable， 需指定包名
    val s = scala.collection.mutable.Map("a" -> 100, "b" -> 90, "c" -> 95)
    
    // 空 map，需声明类型
    val empty = new scala.collection.mutable.HashMap[String, Int]
    
}