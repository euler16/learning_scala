/**
 * Compute functions using Fixed point iterations
 *
 */

val delta = 0.0001

def isCloseEnough(x: Double, y: Double): Boolean = 
  def abs(z: Double): Double = if z >= 0 then z else -z

  if abs((y-x)/x) <= 0.0001 then true else false


def fixedPoint(f: Double => Double) (firstGuess: Double = 1.0): Double = 
  def iterate(guess: Double = 1.0): Double = 
    val eval = f(guess)
    println(s"${eval}")
    if isCloseEnough(guess, eval) then eval
    else iterate(eval)

  iterate(firstGuess)


// sqrt(x) is the fixed point of the function g(y) = x/y
def averageDamp(f: Double => Double)(x: Double): Double = 
  (x + f(x)) / 2

//def sqrt(x: Double): Double = fixedPoint(y => (y + (x/y))/2.0)(1.5)

def sqrt(x: Double): Double = fixedPoint(averageDamp(y => x/y))(1.0)

@main def run = 
  println(sqrt(2))
