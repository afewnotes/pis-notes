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

class Pri(var nameInPri: String, val ageInPri: String) {    // 主构造器与类定义密不可分，参数直接定义在类名后；主构造器会立即执行类定义中的所有语句
    println("hello again")
    
    // def this(test: String) {
    //     this()  // 无法调用
    // }
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
  private java.lang.String nameInPri;
  private final java.lang.String ageInPri;
  public java.lang.String nameInPri();
  public void nameInPri_$eq(java.lang.String);
  public java.lang.String ageInPri();
  public Pri(java.lang.String, java.lang.String);  // 主构造器
}
*/