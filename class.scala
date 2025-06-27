class Rational(val x: Int, val y: Int):
  def this(x: Int) = this(x, 1)
  def numer() = x
  def denom() = y


@main def run = 
  val r = Rational(1, 2)
  val x = Rational(1)
  println(s"${r.numer()} / ${r.denom()}")
  
