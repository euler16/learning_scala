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


/**
 * write a function pack that packs consecutitve duplicates of list
 * elements into sublists.
 *
 * pack(List("a", "a", "a", "b", "c", "c", "c"))
 * should give List(List("a", "a", "a"), List("b"), List("c", "c", "c"))
 *
 *
 */
def pack[T](xs: List[T]): List[List[T]] = xs match
  case Nil      => Nil
  case x :: xs1 => 
    val (more, rest) = xs1.span(y => y == x) // fine usage
    (x :: more) :: pack(rest)


/**
 * Using pack write a function `encode` that produces the run length encoding
 * of a list.
 *
 * The idea is to encode n consecutive duplicates of an element x as a pair (x, n)
 *
 * encode(List("a", "a", "a", "b", "c", "c", "a"))
 * should give
 *
 * List(("a", 3), ("b", 1), ("c", 2), ("a", 1))
 */

def encode[T](xs: List[T]): List[(T, Int)] = 
  pack(xs).map(ys => (ys.head, ys.length))

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



// foldRight vs foldLeft

def concatRight[T](xs: List[T], ys: List[T]): List[T] =
  xs.foldRight(ys)((x, y) => x::y)

def concatLeft[T](xs: List[T], ys: List[T]): List[T] = 
  xs.foldLeft(ys)((x, y) => y::x)


// reverse list using foldLeft
def reverse[T](xs: List[T]): List[T] = xs.foldLeft(List[T]())((xs, x) => x :: xs)

/**
 * Implement map and length functions using foldRight
 */ 

def mapFunc[T](xs: List[T], f: T => T): List[T] = 
  xs.foldRight(List[T]())((curr: T, acc: List[T]) => f(curr) :: acc )


def lengthFunc[T](xs: List[T]): Int = 
  xs.foldRight(0)((curr: T, acc: Int) => 1 + acc)


@main def run = 
  val xs: List[Int] = 1 :: 2 :: 3 :: Nil 
  println(s"implementation: of ${last(xs)}")
  val ys: List[Int] = 4 :: 5 :: 6 :: Nil

  println(s"removing 1 from ${ys} : ${ys.removedAt(1)}")

  val zs = List(List(1, 1), 2, List(3, 4))
  println(s"flattening ${zs} to ${flatten(zs)}")

  val as = List("a", "a", "a", "b", "c", "c", "a")
  println(s"Encoding: ${as} to ${encode(as)}")

  println(s"concatenating: ${concatLeft(xs, ys)}")

  println(s"mapFunc of ${xs}: ${mapFunc(xs, x => x * x)}")
  println(s"get its length: ${lengthFunc(xs)}")
