# notes of [Scala for the impatient]

## 基础

* Scala 中数据类型也是 class
  * 7 种数值类型: Byte, Char, Short, Int, Long, Float, and Double
  * Boolean 类型
* 原始类型与 class 类型无区别，可在数字上调用方法（隐式转换为对应的方法调用，如对 Int 操作转为 RichInt 的方法调用等）
  * `1.toString()`
  * `1.to(10)` // Range(1,2,3,4,5,6,7,8,9,10)
* 无封装类型，Scala 自动处理封包拆包，如字符串底层使用的是 java.lang.String
* 隐式转换的 StringOps 对 String 扩展，包含了上百种操作
  * `"Hello".intersect("World")` // "lo"
* 操作符重载，算数操作符也是方法
  * `1 + 2` 等价于 `1.+(b)`
  * 通常 `a.method(b)` 可简写为 `a method b`
* 递增递减，没有 `++` 和 `--` 操作，使用 `+=1` 和 `-=1` 代替
* BigInt 和 BigDecimal 也可直接使用算数运算符
  * `val x: BigInt = 1234567890`
  * `x * x * x` // Java 需要调用方法 `x.multiply(x).multiply(x)`
* `_` 代表通配符，可表达任意东西

## 函数与方法

* 数学函数使用方便简单，如 min, max 不需要调用某个类的静态方法 （通过 import scala.math.\_ 导入数学函数包）
* 可省略的 scala 包名前缀，如 `import scala.math._` 可写为 `import math._`
* 无静态函数，与之功能类似的是单例对象；通常一个 class 都会有一个伴生对象，其方法就如同 Java 的静态方法
* 无参方法调用时通常不需要带括号
  * `"Hello".distinct`
* `apply` 方法
  * 伴生对象中定义
  * 像函数调用一样使用，可看做是重载了 `()`
  * `"Hello"(4)` 等价于 `"Hello".apply(4)`

## 控制体系&函数

* 条件表达式
  * `if/else` 表达式有返回值
    * `val s = if (a > 0) 1 else -1` // 这种方式下 s 定义为 val，如果放到判断内部赋值，需要定义为变量 var
    * 统一了三目运算 `?:` 和 `if/else`；Scala 无三目运算
    * `if (a) 1` 等价于 `if (a) 1 else ()`；可以将 `()`(，即 Unit 类) 视为无用值的占位符，可看做 Java 中的 void
  * 无 `switch` 表达式，而是使用更为强大的模式匹配来替代
* 语句终结
  * 分号非必须，单行单表达式可省略，单行多表达式需加分号区分
* 语句块&赋值

  * `{...}` 包含一系列表达式，语句块的结果为最后一个表达式的结果
  * 可用于初始化需要多步操作的值
    * `val a = { express1; express2; express3 }`
  * 赋值语句没有返回值，不可使用链式赋值 `x=y=1` // 与预期结果不一致

* IO

  * 打印，`print / println / printf`
  * 读取 console 输入，`readLine / readInt / readDouble...`

* 循环

  * 不像其他语言那么常用，通常可使用单个方法调用完成所有操作
  * while, do
  * 没有与 Java 类似的 for 循环 `for(init; test; update)`，可使用 while 代替，或者使用 for 表达式
    * `for (i <- 1 to 10) r = r * i`
    * 生成器 `variable <- expression` 会遍历所有元素
    * for 循环可包含多个生成器，逗号分隔（或换行区分），可使用 parttern guard 来进行条件过滤
      * `for(v <- exp1; v2 <- exp2 if(condition)) doSome()` // if 之前的分号可省略
    * for 语句中的变量不需要声明 val 或 var，其类型与迭代的集合中元素类型一致
    * `1 to n` 包含上界，`1 until n` 不包含上界
  * 没有 break，continue 表达式来中断循环，替代方案：

    * Boolean 变量控制
    * 嵌套函数
    * 使用 Breaks 对象的 break 方法

    ```scala
    import scala.util.control.Breaks._
    breakable {
        for (...) {
            if (...) break
        }
    }
    ```

  * yield，在 for 循环体以 yield 开始的形式成为 for 推导式
    * 产生的结果为每次迭代的值的集合
      * `for(i <- 1 to 3) yield i % 3` // Vector(1, 2, 0)
    * 生成的集合与第一个生成器类型一致
      * `for(c <- "hello"; i <- 0 to 1) yield (c+i).toChar` // hieflmlmop
      * `for(i <- 0 to 1; c <- "hello") yield (c+i).toChar` // Vector(h, e, l, l, o, i, f, m, m, p)

* 函数

  * Scala has functions in addition to method
  * `trait Function...` 的实例
  * `technically is an object with an apply method`
  * `def abs(x: Double) = if (x >= 0) x else -x`
  * 必须指定所有参数的类型；返回值为`=`右边的表达式或语句块的最后一个表达式的结果；可省略 `return`
  * 如果是递归函数，则必须指明返回类型
    * `def fac(n: Int): Int = if (n <= 0) 1 else n * fac(n - 1)`
  * 参数默认值和命名参数
    * `def decorate(str: String, left: String = "[", right: String = "]") = left + str + right`
    * 调用时可给部分参数，也可给全部参数，还可通过命名参数传值而不考虑参数顺序
      * `decorate("a")` // [a]
      * `decorate("a", "<<")` // <<a]
      * `decorate(left="<", "a")` // <a
  * 可变参数（本质上是一个 Seq 类型的参数）
    * `def sum(args: Int*) ={var result=0; for (a <- args) result += a; result}`
    * `sum(1,2,3)` // 6
    * `sum(1 to 5: _*)` // 15 当传递序列做为参数时，需要添加 `_*` 告诉编译器传入的为参数序列， 而不是 Int

* 过程 Procedures

  * 无返回值的函数
  * 调用过程通常是为了其副作用，如打印等
  * `def box(s: String) { println(s) }` // 无需要 `=`

* `lazy`

  * 延迟加载，变量定义为 lazy 后，会在第一次访问时才被初始化/执行
  * `lazy val words = scala.io.Source.fromFile("/../words").mkString` // if the program never accesses `words`, the file is never opened
  * 减少初始化消耗、解决循环依赖问题等
  * 会有多余开销：每次使用到 lazy 变量时，都会检查该变量是否已经初始化

* `Exceptions`

  * 无受检异常
  * `Nothing`，throw 表达式的返回类型；在 if/else 表达式中，如果一个分支抛出异常，则 if/else 的返回类型为另一个分支的类型
    * `if (x > 0) f(x) else throw new Exception("xx")`
  * catch 语句块中可使用模式匹配来处理对应类型的异常

    ```scala
    try {
        process(xx)
    } catch {  // 优先匹配原则，将最准确的匹配项放在前面，通用的匹配项放在最后
        case ex: IOException => do1()
        case _ => None
    }
    ```

  * 使用 try/finally 来忽略异常

    ```scala
    preStep()  // 此步出错如何处理？

    try {
        process(oo)
    } finally {
        f()    //  此步出错又如何处理？
    }
    ```

## Arrays

* `Array` 固定长度；`ArrayBuffer` 可变长度
  * `arr.toBuffer`, `buf.toArray`
* 初始化是不要使用 `new`
* 使用 `()` 访问元素
* 使用 `for (elem <- arr)` 遍历元素；倒序 `arr.reverse`
* 使用 `for (elem <- arr if ...) ... yield ...` 转换为新的数组
  * 等价于 `arr.filter(...).map(...)` 或者更简洁 `arr filter { ... } map {...}`
* 与 Java 的数组通用，如果是 `ArrayBuffer`， 可配合 `scala.collection.JavaConversions` 使用
* 在做任何操作前都会转换为 `ArrayOps` 对象
* 构建多维数组
  * `val matrix = Array.ofDim[Double](3, 4)` // 3 行 4 列

## Maps & Tuples

* 创建、查询、遍历 Map 的语法便捷
  * `val scores = Map("a" -> 100, "b" -> 90, "c" -> 95)` 创建的默认为 `immutable` 的 hash map
  * 可变的 Map 需要显式指定 `scala.collection.mutable.Map`
  * 创建空的 Map 需指定类型 `new scala.collection.mutable.HashMap[String, Int]`
  * Map 是键值对的集合，键值对类型可不相同
    * `"a" -> 100` 等价于 `("a", 100)`；创建的另一种写法 `Map(("a", 100), ("b", 90), ("c", 95))`
  * 访问
    * `scores("a")` //返回 Option
    * `scores("d").getOrElse(0)` // 返回实际值
  * mutable 更新
    * 更新值 `scores("a") = 80`
    * 增加元素 `scores += ("d" -> 70, "e" -> 50)`
    * 删除元素 `scores -= "a"`
  * immutable 不可更新，修改时会产生新的 Map， 但公共部分的元素数据是共享的
    * 添加元素会产生新的 Map，`scores + ("d" -> 70, "e" -> 50)`
    * 删除元素产生新的 Map `scores - "a"`
  * 遍历 `for((k,v) <- map) ...`
  * 排序 Map
    * 按照 key 排序存放 `scala.collection.immutable.SortedMap("d" -> 1, "b" -> 2, "c" -> 3)` // Map(b -> 2, c -> 3, d -> 1)
    * 按照插入顺序排放 `scala.collection.mutable.LinkedHashMap("d" -> 1, "b" -> 2, "c" -> 3)` // Map(d -> 1, b -> 2, c -> 3)
* 区分 mutable 和 immutable
* 默认 hash map，也可使用 tree map
* 与 Java 中的 Map 转换方便 `scala.collection.JavaConverters`
  * 在很多时候需要使用 Java 的接口完成任务，但是处理结果时可转换为 Scala 的数据接口来处理更方便，如文件操作等
* Tuples 在聚合操作时很有用
  * Map 中的键值对就是最简单的元组形式 `(k, v)`
  * 类型不必一致 `val a = (1, 3.14, "hello")`
  * 下标访问 `a._1` // 1
  * 模式匹配访问 `val (first, second, _) = a`
  * 用于返回多个值
* Zipping
  * 元组可用于绑定多个值同时处理
  * `zip` 方法

## Classes

* 一个源文件可包含多个类，每个类默认都是 public
* 类字段必须初始化，编译后默认是 private，自动生成 public 的 getter/setter ；[Person 示例](sfti/tmp/Person.scala)
  * `private` 字段，生成 private 的 getter/setter
  * `val` 字段，只生成 getter
  * `private[this]` 字段，不生成 getter/setter
  * 自定义 getter/setter，foo 和 foo\_=
* 类方法默认都是 public
* 方法调用规约：访问器调用可省略括号，修改器调用加上括号
* 为字段加上 `@bean.BeanProperty` 注解可生成符合 JavaBean 规范的 get/set 方法（加上默认的两个方法，共四个方法）
* 构造器：1 个主构造器，任意个辅构造器
  * 全部都叫 `this`，只是参数不同
  * 辅构造器必须调用主构造器或之前定义的辅构造器
  * 主构造器与类定义密不可分，参数直接定义在类名后
  * 主构造器会立即执行类定义中的所有语句
  * 主构造器中的参数被方法使用到，则对应的参数等价于 `private[this] val` 字段
* 内部类
  * 路径依赖，不同于 Java 内部类，同一类 A 的不同实例(a1, a2)构建的内部类 Inner，其类型是不同的，a1.Inner != a2.Inner
  * 解决路径依赖
    * 类型投射，Outer#Inner
    * 将内部类放到伴生对象 object 中
  * `self =>` 自身类型，区分调用的内部类和外部类的字段、方法等

## Objects

* 用于单例及工具类方法
  * object 构造器只在第一次被调用时执行
* 可继承一个 `class` 或多个 `trait`
  * 可用于全局默认对象
* 不可提供构造器参数
* 伴生对象
  * 与类名称一致
  * 类与伴生对象可互相访问私有资源，但区分作用域，如 `Accounts.newUniqueNumber()` 而不是 `newUniqueNumber()`
  * 类与伴生对象必须在同一个源文件中
* 伴生对象中的 `apply` 方法
  * 调用方式 `Object(arg1, ..., argN)`， 返回伴生类的实例，如 `Array(1,2,3)`
  * 省略 `new` 关键字，在嵌套表达式中很方便
* 应用对象
  * `extends App`
  * 不需要 main 方法直接执行构造器内的代码
* scala 默认无枚举类型
  * 使用 `Enumeration` 帮助类实现
  * 枚举类型为 `Enumeration.Value(ID, name)` 内部类， ID 依次累加, 默认 0 开始；name 默认是字段名

## Packages and Imports

* `package` 包名和文件路径并不一定对应
* `java.lang`, `scala`, `Predef` 始终默认会导入
* 与 Java 不同，包路径并不是绝对的，如 `collection.mutable` 实际是 `scala.collection.mutable`
* `package a.b.c` 与 `package a { package b { package c {}}}` 不同
  * `package a` 或 `package b` 中定义的资源可在带括号的包声明中访问，但 `package a.b.c` 无法访问
* 包对象
  * package 由于 JVM 的限制不能直接声明函数或变量
  * 不同于 package， package object 可定义工具函数或常量
* 可见性控制，通过 `private[package.name]` 限制资源的可见性
* `import`
  * 导入包后可使用相对路径访问类等，如 `collection.mutable`
  * 导入所有资源 `import collection.mutable._`
  * 可在任意位置进行导入操作
  * `selector`
    * 选择性的导入一部分成员，`import java.awt.{Color, Font}`
    * 为导入成员取别名：`import java.util.{HashMap => JavaMap}`
    * 隐藏成员： `import java.util.{HashMap => _, _}` // 避免产生混淆
  * 隐式导入，默认导入三个 `java.lang`, `scala` 和 `Predef`
    * 后面导入的可将前面的成员覆盖，避免冲突
    * 导入 scala 相关的包可省略 `scala` 路径

## Inheritance

> `fragile base class` 基类被继承之后，修改基类可能会对子类造成无法预期的影响

* 继承类，与 Java 一样使用 `extends` 关键字
  * `final` 类不能被继承， `final` 字段、方法不能被覆盖
* 覆盖非抽象方法，必须使用 `override` 关键字
* 抽象方法
  * 无方法体的方法，可以省略 `abstract` 关键字；子类覆盖时也可以省略 `override`
* 抽象字段
  * 无初始值的字段，可省略 `abstract` 关键字，子类覆盖式也可省略 `override`
* 调用父类方法，使用 `super` 关键字
* 类型检查和转换， `isInstanceOf`, `asInstanceOf`；获取类型, `classOf`
  * 模式匹配通常是个更好的类型检查方式
* `protected` 不同于 Java，受保护成员在包内不可见
* 辅助构造器不可直接调用超类构造器
  * 可在定义类时直接在 extends 时调用超类构造器并传递参数
  * 继承 Java 类时主构造器必须调用超类的构造器
* 覆盖字段
  * `def` 只能覆盖 `def`
  * `val` 只能覆盖 无参数的 `def`
  * `var` 只能覆盖 抽象的 `var`
* 继承层级

  ![Hierarchy](imgs/Hierarchy.png)
  * `Any` 定义了 `asInstanceOf`, `isInstanceOf`，判断相等，hash值等方法
  * `AnyRef` 是除基础类型外所有类的父类，等价于 `java.lang.Object`
    * 提供方法 `wait`, `notify/notifyAll`，`synchronized`
  * `AnyVal` 不包含任何方法，只是个值类型的标记
  * 所有 Scala 类都实现了 `ScalaObject` 这个标记接口，该接口无任何方法
  * `Null` 的唯一实例 `null`，可分配给引用类型，但不可分配给值类型(`Int` 不可为 `null`)
  * `Nothing` 无实例，在泛型构造时有用，`Nil` 类型为 `List[Nothing]`
  * `???` 方法声明返回类型为 `Nothing`， 无返回值，会抛出 `NotImplementedError`，用于预留未实现的方法
  * `Unit` 代表空/`void`，类型唯一值为 `()`
  * 如果方法参数类型为 `Any` 或 `AnyRef`， 当传递多个参数时，会被替换为 `tuple`
* `equals` 和 `hashCode` 判断对象相等
  * 可使用模式匹配实现 `equals`
  * `equals` 参数类型为 `Any` 而不是具体的类型
  * `##` 是 `hashCode` 的安全版本，遇到 `null` 会返回 0 而不是抛出异常
* 值类 Value Class
  * 继承 `AnyVal`
  * 主构造器只有一个参数 val，无构造体
  * 无其他构造器和字段
  * 自动提供的 `equals` 和 `hashCode` 比较实际值
  * 用于隐式转换
    * 其他用途，如 `class a(x: Int, y: Int)` 设计为 `class a(x: X, y: Y)` ，定义值类 `X`, `Y` 避免混淆参数

## Files & RegExp

* 读取所有行 `Source.fromFile({name/java.io.File}).getLines.toArray`，关闭资源 `source.close`
* 读取字符，直接迭代 source
* 读取标准输入 `scala.io.StdIn`
* 读取 URL `Source.fromURL(url, "UTF-8")`
* 读取字符串 `Source.fromString("Hello ww")`
* 读取二进制文件，使用 Java 的库
* 写文件使用 Java 的库
* 序列化 `@SerialVerionUID(42L) class Name extends Serializable`
  * `Serializable` 为 Scala 中的 `trait`
  * 也可省略注解，使用默认的 UID
  * Scala 的集合都是序列化的
* 进程控制
  * 工具包 `scala.sys.process`，包含隐式转换将 `String` 转为 `ProcessBuilder`
  * 执行 shell
    * `"ls -l".!`， `!` 会执行 `ProcessBuilder` 并阻塞直到命令退出并**返回退出码**
    * `"ls -l".!!` 会将**输出作为字符串**返回
    * `#|` 管道： `("ls -l" #| "grep scala").!`
    * `#>` 重定向输出： `("ls -l" #> new File("out.txt")).!`
    * `#>>` 追加：`("ls -l" #>> new File("out.txt")).!`
    * `#<` 重定向输入：
      * `("grep scala" #< new File("out.txt")).!`
      * `("grep html" #< new URL("http://baidu.com")).!`
    * 设置执行目录/环境变量 `Process("ls -l", new File("../"), ("LANG", "en_US")).!`，环境变量为 `(k, v)` 序列
  * 在 Java 项目中执行 Scala 脚本 `ScriptEngine engine = new ScriptEngineManager().getScriptEngineByName("scala")`
* 正则表达式
      * 工具类 `scala.util.matching.Regex`
      * 构造正则对象 `val pattern = "[0-9]+".r`
        * 存在转义、引号等情况时使用 `"""`，`val pattern = """\s+[0-9]+\s+""".r`
      * 捕获组使用括号表示 `val patternName = "([0-9]+) ([a-z]+)".r`
        * 可定义正则变量作为提取器  `val pattern(num, item) = "123 abc"` (`patternName` 与定义的正则名一致)
        * 也可在 for 循环中使用正则变量直接提取捕获组

## Traits

* 替代 Java 中的接口
* 可以有抽象的和具体的方法
  * 在 `trait` 中未实现的方法默认是抽象的 (abstract)
* 类可以实现多个 `trait`，从最后一个开始调用
  * 使用 `extends` 关键字实现
  * 覆盖抽象方法时不需要 `override` 关键字
  * 有多个 `trait` 则对其他的 `trait` 使用 `with` 关键字
* 所有的 Java 接口都可以被当做 `trait` 使用
* 对象也可以添加多个 `trait`，从最后一个开始调用
* 多个 `trait` 的情况下，`super.someMethod` 会根据从右向左的顺序调用下一个 `trait` 的方法
  * 具体调用依赖于使用时的顺序，相比传统的继承更灵活
  * 在多个 mix-in 的情况下，如果父`trait`存在抽象方法，则子`trait`需使用 `abstract override` 关键字，否则 `super.someMethod` 无法编译
* 有初始值的字段/具体字段，都会被添加到子类中
* 无初始值的字段/抽象字段，在非抽象子类中，需要进行初始化
* `trait` 也有构造器
  * 不可以有构造参数，且只有一个构造器
  * 由定义体中的初始化字段和其他语句构成
  * 构造顺序：父类 > 各`trait`从左向右，有父 `trait` 的先构造，共享的父 `trait` 只构造一次 > 子类
