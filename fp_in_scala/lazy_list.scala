enum LazyList[+A]:
  case Empty
  case Cons(hd: () => A, tl: () => LazyList[A]) 
  // this couldn't be  a by-name parameter 
  // because of some scala shenanigans. "Scala doesn't allow parameters of a case class
  // to be by-name parameters and each data constructor of an enum that takes parameters defines
  // a case class. The limitation is the result of each parameter of a case class getting a
  // corresponding public val
  

  // this functionality casn be accessed as cached.head . 
  def head: Option[A] = 
    this match
      case Empty => None
      case Cons(h, _) => Some(h())

  /*def toList: List[A] = 
    this match
      case Empty => List.empty
      case Cons(h, t) => h()::t().toList
  */

  def toList: List[A] = 
    
    @annotation.tailrec()
    def go(acc: List[A], llist: LazyList[A]): List[A] = 
      llist match
        case Empty => acc
        case Cons(h, t) => go(acc:+ h(), t()) // this is slower as appending to a list is O(n)
        // so in total the whole thing becomes O(n2)
        //
    
    @annotation.tailrec()
    def goEff(llist: LazyList[A], acc: List[A]): List[A] = 
      llist match
        case Empty => acc.reverse
        case Cons(h, t) => goEff(t(), h() :: acc)
        

    goEff(this, List.empty) 


  def drop(n: Int): LazyList[A] = 
   
    @annotation.tailrec()
    def loop(n: Int, llist: LazyList[A]): LazyList[A] = 
      if n <= 0 then llist
      else
        llist match
          case Empty => Empty
          case Cons(h, t) => loop(n-1, t())

    loop(n, this)
 
  /*
  def take(n: Int): LazyList[A] = 
    @annotation.tailrec()
    def loop(acc: LazyList[A], llist: LazyList[A], n: Int): LazyList[A] = 
      if n == 0 then acc // what we want is acc.reverse
      else 
        llist match
          case Cons(h, t) => loop(LazyList.cons(h(), acc), t(), n-1)
          case Empty => acc // what we want is acc.reverse

    loop(LazyList.empty, this, n)

  */

  def take(n: Int): LazyList[A] = 
    if n <= 0 then LazyList.empty 
    else 
      this match
        case Empty => LazyList.empty
        case Cons(h, t) => LazyList.cons(
          h(),
          if n == 1 then LazyList.empty
          else t().take(n-1) // note this returns LazyList
        )

object LazyList:
  def cons[A](
    hd: => A,
    tl: => LazyList[A]
  ): LazyList[A] =
    lazy val head = hd
    lazy val tail = tl
    Cons(()=>head, ()=>tail)

  def empty[A]: LazyList[A] = Empty 

  def apply[A](as : A*): LazyList[A] = 
    if as.isEmpty then empty
    else cons(as.head, apply(as.tail*))

  // this functionality can be accessed as LazyList.head(cached)
  def head[A](xs : LazyList[A]): Option[A] =
  {
    println("inside companion object's head. call it as LazyList.head(..)")
    xs match 
      case Empty => None
      case Cons(h, _) => Some(h())
  }

@main def printing():Unit = 
  def readTwice(xs : LazyList[Int]): Unit =
    xs match 
      case LazyList.Cons(head, _) => 
        println(head())
        println(head())

      case LazyList.Empty => ()

  val direct = LazyList.Cons(
    () => { println("Evaluating direct head"); 42 },
    () => LazyList.empty 
  )

  val cached = LazyList.cons(
    { println("Evaluating direct head"); 42 },
    LazyList.empty
  )

  val tester = LazyList.apply(
    1, 2, 3
  )

  println(tester)
  println(tester.toList)

  println(tester.drop(2).toList)
  println(tester.take(2).toList)

  println("Both lists created")
  println("****************")
  readTwice(direct)
  println("****************")
  readTwice(cached)

  println(LazyList.head(cached))
  println(cached.head)


