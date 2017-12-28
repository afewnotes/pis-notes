##### notes of [Scala for the impatient]

###### 基础
- Scala 中数据类型也是 class
    - 7 种数值类型: Byte, Char, Short, Int, Long, Float, and Double
    - Boolean 类型
- 原始类型与 class 类型无区别，可在数字上调用方法（隐式转换为对应的方法调用，如对 Int 操作转为 RichInt 的方法调用等）
    - `1.toString()`
    - `1.to(10)` // Range(1,2,3,4,5,6,7,8,9,10)
- 无封装类型，Scala 自动处理封包拆包，如字符串底层使用的是 java.lang.String
- 隐式转换的 StringOps 对 String 扩展，包含了上百种操作
    - `"Hello".intersect("World")` // "lo"
- 操作符重载，算数操作符也是方法
    - `1 + 2` 等价于 `1.+(b)`
    - 通常 `a.method(b)` 可简写为 `a method b`
- 递增递减，没有 `++` 和 `--` 操作，使用 `+=1` 和 `-=1` 代替
- BigInt 和 BigDecimal 也可直接使用算数运算符
    - `val x: BigInt = 1234567890`
    - `x * x * x`  // Java 需要调用方法 `x.multiply(x).multiply(x)`
- `_` 代表通配符，可表达任意东西

###### 函数与方法
- 数学函数使用方便简单，如 min, max 不需要调用某个类的静态方法 （通过 import scala.math._ 导入数学函数包）
- 可省略的 scala 包名前缀，如 `import scala.math._` 可写为 `import math._`
- 无静态函数，与之功能类似的是单例对象；通常一个 class 都会有一个伴生对象，其方法就如同 Java 的静态方法
- 无参方法调用时通常不需要带括号
    - `"Hello".distinct`
- `apply` 方法
    - 伴生对象中定义
    - 像函数调用一样使用，可看做是重载了 `()`
    - `"Hello"(4)` 等价于  `"Hello".apply(4)`

###### 控制体系&函数
- 条件表达式
    - `if/else` 表达式有返回值
        - `val s = if (a > 0) 1 else -1` // 这种方式下 s 定义为 val，如果放到判断内部赋值，需要定义为变量 var
        - 统一了三目运算 `?:` 和 `if/else`；Scala 无三目运算
        - `if (a) 1` 等价于 `if (a) 1 else ()`；可以将 `()`(，即 Unit 类) 视为无用值的占位符，可看做 Java 中的 void
    - 无 `switch` 表达式，而是使用更为强大的模式匹配来替代
- 语句终结
    - 分号非必须，单行单表达式可省略，单行多表达式需加分号区分
- 语句块&赋值
    - `{...}` 包含一系列表达式，语句块的结果为最后一个表达式的结果
    - 可用于初始化需要多步操作的值
        - `val a = { express1; express2; express3 }`
    - 赋值语句没有返回值，不可使用链式赋值 `x=y=1` // 与预期结果不一致

- IO
    - 打印，`print / println / printf`
    - 读取 console 输入，`readLine / readInt / readDouble...`

- 循环
    - 不像其他语言那么常用，通常可使用单个方法调用完成所有操作
    - while, do
    - 没有与 Java 类似的 for 循环 `for(init; test; update)`，可使用 while 代替，或者使用 for 表达式
        - `for (i <- 1 to 10) r = r * i`
        - `i <- expr` 会遍历所有元素
        - for 语句中的变量不需要声明 val 或 var，其类型与迭代的集合中元素类型一致
        - `1 to n` 包含上界，`1 until n` 不包含上界
    - 没有 break，continue 表达式来中断循环，替代方案：
        - Boolean 变量控制
        - 嵌套函数
        - 使用 Breaks 对象的 break 方法