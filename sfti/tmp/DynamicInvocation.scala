
object Dyn {

  import scala.language.dynamics
  class Person extends Dynamic {
    def selectDynamic(name: String) = {
      println(s"""selectDynamic: $name=123""")
      this
    }
    def updateDynamic(name: String)(args: Any) = {
      println(s"""updateDynamic: $name(${args})""")
      this
    }
    def applyDynamic(name: String)(args: Any*) = {
      println(s"""applyDynamic: $name(${args mkString ","})""")
      this
    } 
    def applyDynamicNamed(name: String)(args: (String, Any)*) = {
      println(s"""applyDynamicNamed: $name(${args mkString ","})""")
      this
    }
  }
  object Person {
    def update(name: String)(args: Any) = println(s"""update: $name=$args""")
  }
  var person = new Person
  person.lastName // selectDynamic: lastName=123
  person.lastName = "Doe" // updateDynamic: lastName(Doe)  // selectDynamic: lastName=123
  person.find("HI", 1, 2, 3) // applyDynamic: find(HI,1,2,3)
  person.find(a = 1, b = 2, c = 3) // applyDynamicNamed: find((a,1),(b,2),(c,3))
  person.find(5) = "10" // selectDynamic: find=123 // applyDynamic: update(5,10)
}
