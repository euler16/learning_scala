def fib(previous : Int, current : Int,  n : Int) : Int = 
  if n == 0 then 
    previous
  else
    fib(current, current + previous, n-1)

@main def printFib : Unit = 
  println(fib(0, 1, 3))
  println(fib(0, 1, 1))
  println(fib(0, 1, 2))
  println(fib(0, 1, 4))
