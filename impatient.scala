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
}