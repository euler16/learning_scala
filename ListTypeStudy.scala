// implementation of last 


def last[T](xs: List[T]): T = xs match 
  case List()  => throw Error("last of empty list")
  case List(x) => x
  case y::ys   => last(ys)

// Important program
def flatten(xs: Any) : List[Any] = xs match
  case Nil => Nil
  case y :: ys => flatten(y) ++ flatten(ys)
  case _ => xs :: Nil

extension [T](xs: List[T])
  def removedAt(n: Int): List[T] = 
    // take till index k
    def takeImpl(k: Int, current: List[T]): List[T] = (current, k) match 
      case (Nil , _)    => Nil
      case (_   , 0)    => Nil
      case (y::ys  , k) => y::takeImpl(k-1, ys)
      

    // drop till index k
    def dropImpl(k: Int, current: List[T]): List[T] = (current, k) match
      case (Nil, _)   => Nil
      case (zs,    0) => zs
      case (y::ys, k) => dropImpl(k-1, ys)

    takeImpl(n, xs) ++ dropImpl(n+1, xs)


@main def run = 
  val xs: List[Int] = 1 :: 2 :: 3 :: Nil 
  println(s"implementation: of ${last(xs)}")
  val ys: List[Int] = 4 :: 5 :: 6 :: Nil

  println(s"removing 1 from ${ys} : ${ys.removedAt(1)}")

  val zs = List(List(1, 1), 2, List(3, 4))
  println(s"flattening ${zs} to ${flatten(zs)}")
