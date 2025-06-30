/**
 *
 * INSERTION SORT
 *
 */ 

def isort(xs: List[Int]): List[Int] = xs match
  case List()  => List()
  case y :: ys => insert(y, isort(ys))


def insert(y: Int, xs: List[Int]): List[Int] = xs match
  case List() => y :: Nil
  case z::zs  =>  {
    if y > z then z :: insert(y, zs)
    else y :: z :: zs
  }



@main def run = 
  val xs = List(3, 5, 2, 6, 1)
  val ys = isort(xs)
  println(ys)
