

def fibonacci(n: Int): Int = 
  def go(x: Int, n_1: Int, n_2: Int): Int = 
    if x == n then (n_1 + n_2) else go(x+1, (n_1 + n_2), n_1)

  n match 
  case 0 => 0
  case 1 => 1
  case 2 => 1
  case n => go(3, 1, 1)

@main def run(n: Int): Unit = 
  println(s"${n}th Fibonacci number is ${fibonacci(n)} ")
