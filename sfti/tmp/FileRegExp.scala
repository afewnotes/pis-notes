package tmp.file

import java.util.function.Consumer

import scala.io.BufferedSource

object FileTest extends App {
  import scala.io.Source
  var source: BufferedSource = Source.fromFile("./scan.txt", "UTF-8")
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

  var buffered: BufferedIterator[Char] = Source.fromFile("./scan.txt", "utf-8").buffered
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

//  buffered.close

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
  val out2 = new ObjectOutputStream(new FileOutputStream("./s.obj"))
  out2.writeObject(s)
  out2.close()
  val in2 = new ObjectInputStream(new FileInputStream("./s.obj"))
  val savedS = in2.readObject().asInstanceOf[Ser]
  println(savedS.name) // abc

  /* 正则表达式 */
  import scala.util.matching.Regex
  val pattern = """\s+[0-9]+\s+""".r
  // 迭代处理
  for (matched <- pattern.findAllIn(" 123 abc,456 def"))
    println(matched) // 123
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
    varPattern.replaceSomeIn(
      message,
      m =>
        vars.lift( // lift 将
          m.matched.tail.toInt // m.matched 返回的匹配字符串， tail 截取第一个字符之后，即 $1 变为 1
      ))

  format("Hello $1 and $0", "foo", "bar") // Hello bar and foo

  // 捕获组
  val patternG = "([0-9]+) ([a-z]+)".r
  for (m <- patternG.findAllMatchIn("123 hello 234 z")) {
    println("m.matched: " + m.matched)
    println("m.group(1): " + m.group(1))
    println("m.start: " + m.start)
    println("m.start(1): " + m.start(1))
  }

  // 为捕获组定义名称
  val patternName = "([0-9]+) ([a-z]+)".r("num", "item")
  // 直接定义
  // val patternName = "([0-9]+) ([a-z]+)".r、
  // 匹配 extractor
  // val patternName(num, item) = "123 abc"
  // num = 123
  // item = abc
  for (m <- patternName.findAllMatchIn("123 abc 234 bcd ")) {
    println(m.group("item"))
    println(m.group("num"))
  }
  // 直接在 for 循环抽取捕获组名
  for (patternName(num, item) <- patternName.findAllMatchIn("123 abc 234 bcd ")) {
    println(num + " " + item)
  }
}

object Exercises extends App {
  // exercise 1
  import scala.io.Source
  import java.io.PrintWriter
  val out = new PrintWriter("reverse.txt")
  Source.fromFile("./scan.txt").getLines.toArray.reverse.foreach(out.println(_))
  out.close

  // exercise 2
  import scala.io.Source
  import scala.collection.mutable.ArrayBuffer
  import java.io.PrintWriter

  val n = 5
  val space = 2
  val src = Source.fromFile("./tabs.txt").getLines
  val out3 = new PrintWriter("./tabs.txt")
  val tab = "\t".r
  for (s <- src) {
    var tmp = 1
    out3.println(tab.replaceSomeIn(s, m => {
      if (tmp <= n) {
        tmp += 1
        Some(" " * space)
      } else None
    }))
  }
  out3.close

  // exercise 3
  import scala.io.Source
  Source
    .fromFile("./scan.txt")
    .mkString
    .split("\\s+")
    .filter(_.length > 12)
    .foreach(println)

  // exercise 4
  import scala.io.Source
  val d =
    Source.fromFile("./doubles.txt").mkString.split("\\s+").map(_.toDouble)
  println(
    "sum=" + d.sum + " avg=" + d.sum / d.size + " max=" + d.max + " min=" + d.min)

  // exercise 5
  import java.io.PrintWriter

  val out4 = new PrintWriter("./numbers.txt")
  for (i <- 0 to 20) {
    out4.println("%8.0f  %f".format(Math.pow(2, i), Math.pow(2, -i)))
  }
  out4.close

  // exercise 6
  import scala.io.Source
  val pattern =
    "\"(([^\\\\\"]+|\\\\([btnfr\"'\\\\]|[0-3]?[0-7]{1,2}|u[0-9a-fA-F]{4}))*)\"".r
  for (pattern(s, _, _) <- pattern.findAllMatchIn(
         Source.fromFile("./quote.txt").mkString)) {
    println(s)
  }

  // exercise 7
  import scala.io.Source
  val pattern7 = "\\d+\\.\\d+".r
  val tokens = Source.fromFile("./doubles.txt").mkString
  pattern7.replaceAllIn(tokens, "").split("\\s+").foreach(println(_))

  // exercise 8
  import scala.io.Source
  val html = Source.fromURL("http://horstmann.com", "UTF-8").mkString
  val srcPattern =
    """(?is)<\s*img[^>]*src\s*=\s*['"\s]*([^'"]+)['"\s]*[^>]*>""".r
  for (srcPattern(s) <- srcPattern findAllIn html) println(s)

  // exercise 9
  import java.nio.file._
  import scala.collection.JavaConverters._  // tricks

  Files
    .walk(Paths.get("./"))
    .iterator
    .asScala
    .toStream
    .filter(p => p.getFileName.toString.endsWith(".class"))
    .foreach(println(_))

  // exercise 10
  import scala.collection._
  @SerialVersionUID(42L) class Person(val name: String, friends: Person*) extends Serializable {
    private var _friends = new mutable.HashSet[Person]()
    
    addFriends(friends: _*)

    def addFriends(friends: Person*): Array[Person] = {
      for (f <- friends) _friends.add(f)
      getFriends
    }

    def getFriends = _friends.toArray

    override def toString = s"Person[$name, friends=${_friends}]"
  }
  val p1 = new Person("Joe")
  val p2 = new Person("Doe", p1)
  val p3 = new Person("Foo", p1, p2)

  import java.io._
  val out11 = new ObjectOutputStream(new FileOutputStream("./friends.obj"))
  out11.writeObject(List(p1,p2,p3))
  out11.close

  val in = new ObjectInputStream(new FileInputStream("./friends.obj"))
  val des = in.readObject().asInstanceOf[List[Person]]
  for (p <- des) println(p)
}
