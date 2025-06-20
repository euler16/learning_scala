/*class Cafe:
  def buyCoffee(cc: CreditCard): Coffee = 
    val cup = Coffee()
    cc.charge(cup.price)
    cup

class CreditCard:
  def charge(price: Double): Unit =
    println("charging " + price)

class Coffee:
  val price: Double = 2.0


@main 
def start(): Unit = 
  val cc   = CreditCard()
  val cafe = Cafe()
  val cup  = cafe.buyCoffee(cc)
*/


// functional solution

class Cafe:
  def buyCoffee(cc: CreditCard): (Coffee, Charge) = 
    val cup = Coffee()
    (cup, Charge(cc, cup.price))

class Coffee:
  val price: Double = 2.0

class CreditCard:
  def charge(price: Double): Unit =
    println("charging " + price)


case class Charge(cc: CreditCard, amount: Double):
  def combine(other: Charge): Charge = 
    if cc == other.cc then
  Charge(cc, amount + other.amount) // a case class can be created without new
    else 
      throw Exception("Can't combine charges with different cards")



@main
def run(): Unit = 
  println()
  val cc = CreditCard()
  val cafe = Cafe()
  val (cup, charge) = cafe.buyCoffee(cc)

