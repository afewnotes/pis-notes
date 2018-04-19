object Annotations {
  
  // 尾递归优化

  def sum(xs: Seq[Int]): BigInt = if (xs.isEmpty) 0 else xs.head + sum(xs.tail)

  // @tailrec 会尝试优化尾递归调用，将其变为循环
  def sum2(xs: Seq[Int], partial: BigInt): BigInt = 
    if (xs.isEmpty) partial else sum2(xs.tail, xs.head + partial)

  val xs = 1 to 1000000
  // sum(xs) // 栈溢出
  sum2(xs, 0) // 500000500000
}
