import scala.beans.BeanProperty

class Person {
    var age = 0 // 默认生成 private 字段及 public 的访问器/修改器
    
    private var g = 0  // 生成 private 的字段及访问器/修改器
    
    val a = 0  // 生成 private final 常量及 public 的访问器
    
    private val b = 0  // 生成 private final 常量及 private 的访问器
    
    private[this] val c = 0  // 生成 private final 常量，无访问器/修改器
    private[this] var d = 0  // 生成 private 字段，无访问器/修改器
    
    @BeanProperty var bean: String = _
}

/*
Compiled from "Person.scala"
public class Person {
  private int age;
  private int g;
  private final int a;
  private final int b;
  private final int c;
  private int d;
  public int age();
  public void age_$eq(int);   // 实际为 age_=  由于 JVM 不允许方法名包含等号，所以使用 $eq 代替
  private int g();
  private void g_$eq(int);
  public int a();
  private int b();
  
  public java.lang.String bean();
  public void bean_$eq(java.lang.String);
  public java.lang.String getBean();  // 除了生成默认的 name 和 name_$eq 方法外，还生成了 JavaBean 规范的方法 get/set
  public void setBean(java.lang.String);
  
  public Person();
}
*/