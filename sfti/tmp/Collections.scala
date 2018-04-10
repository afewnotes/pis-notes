object Collections {
  def multi(l: List[Int]): Int = l match {
    case Nil    => 1
    case h :: t => h * multi(t)
  }
}
