/*
package com {
    object Utils {
        def percentOf(value: Double, rate: Double) = value * rate / 100
    }
    package foo {
        class A {
            var salary = 0

            def giveRaise(rate:scala.Double) {
                // 直接调用 Utils，不需声明路径
                salary +  = Utils.percentOf(salary, rate)
            }
        }
    }
}

package org {
    package bar {
        class B
    }
} */

//  简化 package 声明，直接放在文件头部，不需要括号
package com.foo.bar

// 包对象
package object people {
  val defaultName = "zhangsan"
}

package people {
  class Person {
    // 可见性控制
    private[people] var name = defaultName
  }
}

/* exercise 1 */

package com.horstmann {
  object top1 {
    val me: String = "Hello"
  }
}

package com {
  package horstmann {
    package impatient {
      class A {
        println(top.me + "---")
      }
    }
  }
}

package com.horstmann.impatient {
  class B {
    //   println(top.me)  // error
  }
}
package com.horstmann {
  object top {
    val me: String = "Hello"
  }
}

package com {
  package horstmann {
    package impatient {
      class A1 {
        println(top.me + "---")
      }
    }
  }
}

package com.horstmann.impatient {
  class B1 {
    //   println(top.me)  // error
  }
}

/* exercise 3 */
package object Random {
  private var v: Int = 0
  val a: Int = 1664525
  val b: Int = 1013904223
  val n: Int = 32

  def nextInt(): Int = {
    v = (v * a + b) % scala.math.pow(2, n).toInt
    v
  }
  def nextDouble(): Double = nextInt.toDouble
  def setSeed(seed: Int) = {
    v = seed
  }
}

/* exercise 6 & 7*/

object Copy extends App {
    import java.util.{HashMap => JavaHashMap}   
    val jh = new JavaHashMap[String, Int]
    jh.put("a", 1)
    jh.put("b", 2)

    import scala.collection.mutable._
    val sh = HashMap[String, Int]()
    val it = jh.entrySet().iterator()
    while (it.hasNext()) {
        val pairs = it.next()
        sh += (pairs.getKey() -> pairs.getValue())
    }

    sh.foreach(println)
}

/* exercise 9 */
import java.lang.System
object Secret extends App {
    val name = System.getProperties.getProperty("user.name")
    if (args.length > 0 && args(0) == "secret") {
        println("Welcome " + name)
    } else {
        System.err.println("Wrong password")
    }
}

