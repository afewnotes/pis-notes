object FileTest extends App {
  import scala.io.Source
  var source = Source.fromFile("./scan.txt", "UTF-8")
  println(source)
  val lineIterator = source.getLines
  // 迭代处理
  for (l <- lineIterator) println(l)

// 消费之后迭代器为空，重新读取文件
  source = Source.fromFile("./scan.txt", "UTF-8")
  // 转为 Array 或 ArrayBuffer
  val lineArr = source.getLines.toArray

  // 直接读取为字符串
  source = Source.fromFile("./scan.txt", "UTF-8")
  println(source.mkString(", "))

  // 读取完毕，关闭资源
  source.close


  var buffered = Source.fromFile("./scan.txt", "utf-8").buffered
  // for (i <- buffered) print(i)

  // buffered.head 返回第一个字符，但不会消费掉
  while (buffered.hasNext) {
    print(buffered.next)
  }

  buffered.close
}
