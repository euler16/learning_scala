enum LazyList[+A]:
  case Empty
  case Cons(hd: () => A, tl: () => LazyList[A]) 
  // this couldn't be  a by-name parameter 
  // because of some scala shenanigans. "Scala doesn't allow parameters of a case class
  // to be by-name parameters and each data constructor of an enum that takes parameters defines
  // a case class. The limitation is the result of each parameter of a case class getting a
  // corresponding public val

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
    () => { println("Evaluating direct head"); 42 },
    () => LazyList.empty
  )

  println("Both lists created")
  println("****************")
  readTwice(direct)
  println("****************")
  readTwice(cached)


