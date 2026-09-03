// the goal of this program is to test if traits can themselves create objects 


trait Payments:
  def hello(): Unit = println("hello trait object works")


val traitObj = Payments()
