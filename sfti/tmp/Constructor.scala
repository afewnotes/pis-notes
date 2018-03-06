class Constructor() {    
    private var name = ""
    private var age = 0
    
    def this(name: String) {  // 辅构造器
        this()  // 调用主构造器
        this.name = name
    }
    
    def this(name: String, age: Int) {  // 辅构造器
        this(name)  // 调用之前定义的辅构造器
        this.age = age
    }
}

class Pri(nameInPri: String, ageInPri: String) {    // 主构造器与类定义密不可分，参数直接定义在类名后；主构造器会立即执行类定义中的所有语句
    println("hello again")
    
    // def this(test: String) {
    //     this()  // 无法调用
    // }
    def param2 = "hello: " + nameInPri
}

/*
-$ javap -p Constructor
Compiled from "Constructor.scala"
public class Constructor {
  private java.lang.String name;
  private int age;
  private java.lang.String name();
  private void name_$eq(java.lang.String);
  private int age();
  private void age_$eq(int);
  public Constructor();
  public Constructor(java.lang.String);
  public Constructor(java.lang.String, int);
}
-$ javap -p Pri
Compiled from "Constructor.scala"
public class Pri {
  private final java.lang.String nameInPri;  // param2 使用到了 nameInPri，因此 nameInPri 变为字段
  public java.lang.String param2();
  public Pri(java.lang.String, java.lang.String);  // 主构造器
}
*/