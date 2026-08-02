class Cafe : 
  def buyCoffee(cc : CreditCard) : (Coffee, Charge) = 
    val coffee = Coffee()
    (coffee, Charge(cc, coffee.price))

class Charge(cc : CreditCard, amount : Double) : 
  def coalesce(c2 : Charge) : Charge =
    if cc == c2.cc then 
      Charge(cc, amount + c2.amount)
    else 
      throw Exception("Can't combine charges on different credit card")


class Coffee
  val price : Double = 2.0

    
