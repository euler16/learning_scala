/**
 * write a product function that calculates the product of the values of a function for the points on
 * a given interval
 *
 * write factoral in terms of product
 *
 * write a more general function, which generalises both sum and product
 *
 * */


def product(f: Int => Int)(a: Int, b: Int): Int = 
  if a > b then 1
  else f(a) * product(f)(a+1, b)


def factorial(n: Int): Int =
  product(x => x)(1, n)


def mapReduce(f: Int => Int)(combine: (Int, Int) => Int, identity: Int)(a: Int, b: Int): Int = 
  if a > b then identity
  else combine(f(a), mapReduce(f)(combine, identity)(a+1, b))


def sum(f: Int => Int) = mapReduce(f)((x: Int, y: Int) => x + y, 0)


@main def run(a: Int, b: Int): Unit = 
  println(product(x => x)(a, b))
  println(s"factorial : ${factorial(b)}")
  println(s"sum : ${mapReduce(x=>x)(_ + _, 0)(a, b)}")
  println(s"product : ${mapReduce(x=>x)(_ * _, 1)(a, b)}")
