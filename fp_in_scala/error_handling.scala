object Chapter4:
  enum Option[+A]:
    case None
    case Some(get: A)

    def map[B](f: A => B): Option[B] = 
      this match
        case None => None
        case Some(get) => Some(f(get))
    def flatMap[B](f: A => Option[B]): Option[B] = 
      this match
        case None => None
        case Some(get) => f(get)
    def getOrElse[B >: A](default : => B): B = // this argument syntax defines laziness
      this match
        case None => default
        case Some(get) => get
    def orElse[B >: A](ob: => Option[B]): Option[B] = 
      /*this match
        case None => ob
        case Some(value) => Some(value)
      */
      // this function is used in place of getOrElse when it needs chaining further. 
      // normally getOrElse is the end of the evaluation chain and contains error handling code
       map(Some(_)).getOrElse(ob)
      
    def filter(f: A => Boolean): Option[A] =
      // this is the function that uses boolean predicates
      this match
        case Some(value) if f(value) => Some(value)
        case _ => None
  
  def mean(xs: Seq[Double]): Option[Double] =
    if xs.isEmpty then Option.None
    else Option.Some(xs.sum / xs.length)
  
  def variance(xs: Seq[Double]): Option[Double] =
    mean(xs).flatMap(m => mean(xs.map(x => math.pow(x - m, 2))))

  def lift[A, B](f: A=>B): Option[A]=>Option[B] = 
    _.map(f) // this is the shorthand for a: Option[A] => a.map(f)
   
  def map2[A, B, C](a : Option[A], b: Option[B])(f: (A, B) => C): Option[C] = 
    /*
    (a, b) match 
      case (None, _) => None
      case (_, None) => None
      case _ => Some(f(a, b))
    */
    // better below
    /*(a, b) match
      case (Some(aa), Some(bb)) => Some(f(aa, bb))
      case _ => None
    */
    // even better
    a.flatMap(aa => b.map(bb => f(aa, bb)))

  def sequence[A](as: List[Option[A]]):Option[List[A]] =
    as match
      case Nil => Option.Some(Nil)
      case head::tail => head match
                            case Option.None => Option.None
                            case Option.Some(hh) => sequence(tail) match
                                                case Option.None => Option.None
                                                case Option.Some(tt) => Option.Some(hh::tt)


  def sequence2[A](as: List[Option[A]]): Option[List[A]] = 
    as match
      case Nil => Option.Some(Nil)
      case head::tail => head.flatMap(hh => sequence2(tail).map(tt => (hh::tt)))

  /*
  def sequence3[A](as: List[Option[A]]): Option[List[A]] = 
    foldRight(as, Some(Nil), (head, accumulated) => head.flatMap(hh => accumulated.map(tt => (hh :: tt))))
  */
  def traverseExistingOptions[A, B](as: List[Option[A]])(f: A => Option[B]): Option[List[B]] = 
    as.foldRight(Option.Some(Nil))((head, accumulated) => {
      head.flatMap(f).flatMap(hh => accumulated.map(tt => (hh::tt)))
    })

  def 



@main def printer(): Unit = 
  import Chapter4.*


  val l = List(1.0, 2.0, 3.0, 4.0)
  println(variance(l).getOrElse(0))
  val p : List[Option[Int]] = List(Option.Some(1), Option.Some(2), Option.Some(3))   
  // println(sequence3(p))
  println(traverseExisting(p)(a => Option.Some(a)))


