// implement && operator without using it 
// need to take care of short circuiting as well

def loop : Boolean = loop // infinite loop
def and(x: Boolean, y: => Boolean): Boolean = if x == true then y else false

@main def run : Unit = 
  println(and(false, loop)) // shouldn't enter infinite loop
