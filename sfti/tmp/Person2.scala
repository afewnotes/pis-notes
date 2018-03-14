class Person2(val name: String) {
  override def toString = getClass.getName + "[name=" + name + "]"
}

class SecretAgent2(codename: String) extends Person2(codename) {
  override val name = "secret" // Don't want to reveal name...
  override val toString = "secret" //... or class name
}
/*
Compiled from "Person2.scala"
public class Person2 {
  private final java.lang.String name;
  public java.lang.String name();
  public java.lang.String toString();
  public Person2(java.lang.String);
}

Compiled from "Person2.scala"
public class SecretAgent2 extends Person2 {
  private final java.lang.String name;
  private final java.lang.String toString;
  public java.lang.String name();
  public java.lang.String toString();
  public SecretAgent2(java.lang.String);
}
 */
