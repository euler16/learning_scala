enum List[+A]:
  case Nil 
  case Cons(head: A, tail: List[A])

object List:
  def apply[A](as: A*): List[A] = 
    if as.isEmpty then Nil 
    else Cons(as.head, apply(as.tail*))

  def tail[A](xs: List[A]): List[A] = xs match 
    case Nil => Nil
    case Cons(head: A, tail: List[A]) => tail

  def drop[A](xs: List[A], n: Int): List[A] = 

    if n == 0 then xs
    else xs match 
      // drop the first n element
      case Nil => Nil
      case Cons(head: A, tail: List[A]) => drop(tail, n-1)


  def dropWhile[A](as: List[A], f : A => Boolean): List[A] = 
    as match 
      case Nil => Nil
      case Cons(head: A, tail: List[A]) => 
        if f(head) then dropWhile(tail, f)
        else as


  def init[A](as: List[A]): List[A] = as match
    case Nil => Nil
    case Cons(_, Nil) => Nil
    case Cons(h: A, tail: List[A]) => Cons(h, init(tail))

  
  def length[A](as: List[A]): Int =
    foldRight(as, 0, (_, len) => 1 + len)

      

def foldRight[A, B](xs: List[A], acc: B, f : (A, B) => B): B = 
  xs match
    case List.Nil => acc
    case List.Cons(x, tail) => f(x, foldRight(tail, acc, f))



def foldLeft[A,B](as: List[A], acc: B,  f : (B, A) => B): B = 
  as match 
    case List.Nil => acc
    case List.Cons(head, tail) => foldLeft(tail, f(acc, head), f)


def append[A](xs: List[A], ys: List[A]): List[A] =
  xs match
    case List.Nil => ys
    case List.Cons(head, tail) => List.Cons(head, append(tail, ys))

def flatMap[A, B](as: List[A], f: A => List[B]): List[B] = 
  foldLeft(as, List.Nil, (acc: List[B], a: A) => append(acc, f(a)))

def flatMap2[A,B](as: List[A], f: A => List[B]): List[B] = 
  foldRight(as, List.Nil, (a, acc) => append(f(a), acc))




@main def testList(): Unit = 
  val l = List(4, 5, 6)
  println(List.tail(l))
  println(List.drop(l, 2))
  println(List.dropWhile(l, x => x % 2 == 0))
  println(List.init(l))



