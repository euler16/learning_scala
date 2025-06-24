/**
 * Demonstrating higher order function with summation
 *
 */


def sumInts(a: Int, b: Int): Int = 
  if a > b then 0 else a + sumInts(a+1, b)

def cube(x: Int): Int = x * x * x

def sumCubes(a: Int, b: Int): Int = 
  if a > b then 0 else cube(a) + sumCubes(a+1, b)


def sum(f: Int => Int, a: Int, b: Int): Int = 
  if a > b then 0 else f(a) + sum(f, a+1, b)


// tail recursive version
def sum(f: Int => Int, a: Int, b: Int): Int = 
  def loop(a: Int, accumulator: Int): Int =
    if a > b then accumulator
    else loop(a+1, f(a) + accumulator)
  loop(a, 0)

// alternate
def sum(f: Int => Int): (Int, Int) => Int
  def sumF(a: Int, b: Int): Int = 
    if a > b then 0 else f(a) + sumF(a+1, b)
  sumF



def sumInts(a: Int, b: Int)  = sum(x:Int => x, a, b)
def sumCubes(a: Int, b: Int) = sum(x:Int => x, a, b) 
