/**
 * Implement iterative version of sqrt function,
 * Based on Newton's method
 *
 * 1. take a seed y = 1
 * 2. then the next approximation is average of y and x/y
 *
 */

def abs(x: Double): Double = if x >= 0 then x else -x
def isGoodEnough(y: Double, x: Double): Boolean = abs(y * y - x) < 1e-5

def SqrtIter(x: Double, y: Double = 1.0): Double =
  if isGoodEnough(y, x) then y 
  else SqrtIter(x, (y + (x/y))/2.0)

@main def run: Unit = 
  println(SqrtIter(2.0, 1.0))
