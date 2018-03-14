class Creature {
  def range: Int = 10
  val env: Array[Int] = new Array[Int](range)
}

class Ant extends Creature {
  override val range = 2
}
/*

Compiled from "Creature.scala"
public class Creature {
  private final int[] env;
  public int range();
  public int[] env();
  public Creature();
}

// ----  def range
Compiled from "Creature.scala"
public class Ant extends Creature {
  public int range();
  public Ant();
}

// ---- val range
Compiled from "Creature.scala"
public class Ant extends Creature {
  private final int range;
  public int range();
  public Ant();
}
 */
