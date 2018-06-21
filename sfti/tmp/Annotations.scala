import scala.annotation.{elidable, switch, tailrec}

object Annotations {
  
  // 尾递归优化

  def sum(xs: Seq[Int]): BigInt = if (xs.isEmpty) 0 else xs.head + sum(xs.tail)

  // @tailrec 会尝试优化尾递归调用，将其变为循环
  @tailrec
  def sum2(xs: Seq[Int], partial: BigInt): BigInt = 
    if (xs.isEmpty) partial else sum2(xs.tail, xs.head + partial)

  val xs = 1 to 1000000
  // sum(xs) // 栈溢出
  sum2(xs, 0) // 500000500000

  // @switch 检查是否编译为 tableswitch 或 lookupswitch
  def s(n: Int) = (n: @switch) match {
    case 0 => "Zero"
    case 1 => "One"
    case _ => "?"
  }
  // javap -c Annotations.class
//
//  public java.lang.String s(int);
//  Code:
//    0: iload_1
//  1: istore_2
//  2: iload_2
//  3: tableswitch   { // 0 to 1
//    0: 34
//    1: 29
//    default: 24
//  }
//  24: ldc           #65                 // String ?
//  26: goto          36
//  29: ldc           #67                 // String One
//  31: goto          36
//  34: ldc           #69                 // String Zero
//  36: areturn

  // 编译时设置参数，该方法代码不会生成  scalac -Xelide-below FINE xxx.scala
  @elidable(elidable.FINE) def dump(props: Map[String, String]) { }


  def allDifferent[@specialized T](x: T, y: T, z: T) = println(s"$x, $y, $z")
//
//  public <T> void allDifferent(T, T, T);
//  public void allDifferent$mZc$sp(boolean, boolean, boolean);
//  public void allDifferent$mBc$sp(byte, byte, byte);
//  public void allDifferent$mCc$sp(char, char, char);
//  public void allDifferent$mDc$sp(double, double, double);
//  public void allDifferent$mFc$sp(float, float, float);
//  public void allDifferent$mIc$sp(int, int, int);
//  public void allDifferent$mJc$sp(long, long, long);
//  public void allDifferent$mSc$sp(short, short, short);
//  public void allDifferent$mVc$sp(scala.runtime.BoxedUnit, scala.runtime.BoxedUnit, scala.runtime.BoxedUnit);

  def allDifferent2[@specialized(Long, Double) T](x: T, y: T, z: T) = println(s"$x, $y, $z")

//  public void allDifferent2$mDc$sp(double, double, double);
//  public void allDifferent2$mJc$sp(long, long, long);
}
