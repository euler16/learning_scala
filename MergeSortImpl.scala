

def mergeSort[T](xs: List[T])(lt : (T, T) => Boolean): List[T] = 
  val n = xs.length / 2
  if n == 0 then xs
  else
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match 
      case (_, Nil) => xs
      case (Nil, _) => ys
      case (x :: xs1, y :: ys1) => 
        if lt(x, y) then y :: merge(xs, ys1)
        else x :: merge(xs1, ys)

    val (fst, snd) = xs.splitAt(n)
    merge(mergeSort(fst)(lt), mergeSort(snd)(lt))



@main def run = 
  val xs = List(4, 3, 9, 8, 1, 2, 0)
  println(s"Sorting ${xs} : ${mergeSort(xs)((x: Int, y: Int) => x < y)}")

