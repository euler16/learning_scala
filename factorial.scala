


// tail recursion optimised
def factorial(n: Int): Int =
  def go(num: Int, accumulator: Int): Int = 
    if num == 0 then accumulator 
    else go(num-1, num*accumulator)

  go(n, 1)



@main def run(x: Int): Unit = 
  println(factorial(x))
