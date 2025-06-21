// a simple scala program
/* multi line comment */


object MyProgram:
/**
 * Documentation
 * `object` declares a singleton object which simultaneously
 * declares a class and its only instance
 * */
  def abs(n: Int): Int = 
    if n < 0 then -n 
    else n


  private def formatAbs(x: Int) = 
    /* can be only accessed by other members of MyProgram */
    val msg = "The absolute value of %d is %d"
    msg.format(x, abs(x))

  // Unit is like void
  @main def printAbs: Unit = 
    println(formatAbs(-42))
