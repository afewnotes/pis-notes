/* 
package com {
    object Utils {
        def percentOf(value: Double, rate: Double) = value * rate / 100
    }
    package foo {
        class A {
            var salary = 0

            def giveRaise(rate: scala.Double) {
                // 直接调用 Utils，不需声明路径
                salary += Utils.percentOf(salary, rate)
            }
        }
    }
}

package org {
    package bar {
        class B
    }
}

 */

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