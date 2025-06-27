class Rational(val x: Int, val y: Int):

  def numer() = x
  def denom() = y


@main def run = 
  val r = Rational(1, 2)
  println(s"${r.numer()} / ${r.denom()}")
  
