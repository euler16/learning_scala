

def loop: Int = loop
// this is call by value, will cause infinite loop
// def first(x: Int, y: Int): Int = x

// this is call by name 
def first(x: Int, y: => Int): Int = x


@main def run: Unit = 
  println(first(1, loop))
