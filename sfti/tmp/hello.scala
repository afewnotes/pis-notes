class test {
    def m1(x:Int) = x+3
    val f1 = (x:Int) => x+3
}

object impatient {
    def signum(n: Int): Int = {
        if (n > 0) 1 else -1
    }
    
    def apply(n: Int): Int = {
        signum(n)
    }
    
    def main(args: Array[String]) {
    //  for (i <- 0 to 10 reverse) println(i)
        countDown(10)
    }
    
    def countDown(n: Int) {
        for(i <- 0 to n reverse) println(i)
    }
    
    def product(s: String): Int = {
        (for (c <- s) c.toInt).product
        
        // s.map(_.toInt).product
        
        // s.head.toInt * product(s.tail)
    }
    
    def productR(s: String):Int = s match {
      case "" => 1
      case _ => s.head.toInt * productR(s.tail)
    }
    
    def compute(x: Int, n: Int): Int = {
        if (n == 0) 1
        else if (n < 0) 1 / compute(x, -n)
        else if (n % 2 == 0) power(compute(x, n / 2), 2).toInt
        else x * compute(x, n - 1)
    }
    
}