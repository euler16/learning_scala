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
    
@main def printer(): Unit = 
  import Chapter4.*


  val l = List(1.0, 2.0, 3.0, 4.0)
  println(variance(l).getOrElse(0))
     


