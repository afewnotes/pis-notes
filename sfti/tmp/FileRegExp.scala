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

  // 读取空格分隔的字符
  val tokens = buffered.mkString.split("\\s+") // res7: Array[String] = Array(a, b, c, d, e, c, d, helo, d, 中文)

// 转换处理……
  var concat = for (w <- tokens) yield w + "$"

  var map = tokens.map(_ + "#")

  buffered.close

// stdin
  print("hi " + io.StdIn.readLine)

  /* 读写文件使用 Java 库 */
  import java.io.{File, FileInputStream, PrintWriter}
  val file = new File("./scan.txt")
  val in = new FileInputStream(file)
  val bytes = new Array[Byte](file.length.toInt)
  in.read(bytes)
  val out = new PrintWriter("./scan-cp.txt") // 乱码未处理
  for (i <- bytes) out.write(i)
  out.close
  in.close

  import java.nio.file._
  val dirname = "../"
  val entries = Files.walk(Paths.get(dirname))
  try {
    entries.forEach(new Consumer[Path] { def accept(p: Path) = println(p) })
  } finally {
    entries.close
  }

/* 序列化&反序列化 */
  @SerialVersionUID(42L) class Ser(val name: String) extends Serializable
  val s = new Ser("abc")
  import java.io._
  val out = new ObjectOutputStream(new FileOutputStream("./s.obj"))
  out.writeObject(s)
  out.close()
  val in = new ObjectInputStream(new FileInputStream("./s.obj"))
  val savedS = in.readObject().asInstanceOf[Ser]
  println(savedS.name) // abc


/* 正则表达式 */
  import scala.util.matching.Regex
  val pattern = """\s+[0-9]+\s+""".r
  // 迭代处理
  for (matched <- pattern.findAllIn(" 123 abc,456 def"))
    println(matched)  // 123
  // 转为数组
  var a = pattern.findAllIn(" 123 abc,456 def").toArray //  Array(" 123 ")
  // 第一个匹配项
  var f = pattern.findFirstIn(" 123 345 ") // f: Option[String] = Some( 123 )
  // 替换第一个匹配
  pattern.replaceFirstIn(" 123 234 ", "#replaced#") // "#replaced#234 "
  // 替换所有匹配
  pattern.replaceAllIn(" 123 234 ", "#replaced#") // "#replaced#234 "
  pattern.replaceAllIn(" 123 , 234 ", "#replaced#") // #replaced#,#replaced#
  // 替换符合条件项
  pattern.replaceSomeIn(" 123 , 22 ", 
    m => if (m.matched.length > 4) Some("#OK#") else None)

  // 替换占位符
  val varPattern = """\$[0-9]+""".r
  def format(message: String, vars: String*) = 
    varPattern.replaceSomeIn(message, m => vars.lift(  // lift 将
      m.matched.tail.toInt  // m.matched 返回的匹配字符串， tail 截取第一个字符之后，即 $1 变为 1
    ))

  format("Hello $1 and $0", "foo", "bar")  // Hello bar and foo
}
