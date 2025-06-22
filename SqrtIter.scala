/**
 * Implement iterative version of sqrt function,
 * Based on Newton's method
 *
 * 1. take a seed y = 1
 * 2. then the next approximation is average of y and x/y
 *
 */
def sqrt(x: Double, y: Double = 1.0): Double = 
  def abs(x: Double): Double = if x >= 0 then x else -x
  def isGoodEnough(y: Double): Boolean = abs(y * y - x)/x < 1e-10 // going for relative error 

  def SqrtIter(y: Double = 1.0): Double =
    if isGoodEnough(y) then y 
    else SqrtIter((y + (x/y))/2.0)

  SqrtIter(y)

@main def run(x: Double, y: Double = 1.0): Unit = 
  println(sqrt(x, y))
