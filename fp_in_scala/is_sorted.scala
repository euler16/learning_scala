def isSorted[A](arr: Array[A], cmp : (A,A) => Boolean): Boolean =

  @annotation.tailrec
  def loop(curr: Int): Boolean = 
    if curr < 0 then false
    else if curr < arr.length-1 then 
      if cmp(arr(curr),  arr(curr + 1)) then loop(curr + 1)
      else false
    
    else true

  loop(0)

@main def checkSorted(): Unit = 
  val array1: Array[Int] = Array(1,2,3)
  val array2: Array[Int] = Array(6,5,4)

  println(isSorted(array1, (x, y) => (x <= y)))
  println(isSorted(array2, (x, y) => (x <= y)))
