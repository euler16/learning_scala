trait RNG:
    def nextInt: (Int, RNG)

case class SimpleRNG(seed: Long) extends RNG:
    def nextInt: (Int, RNG) =
        val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
        val nextRNG = SimpleRNG(newSeed)
        val n = (newSeed >>> 16).toInt
        (n, nextRNG)


type Rand[+A] = RNG => (A, RNG)

def unit[A](a: A): Rand[A] =
    rng => (a, rng)

def map[A, B](s: Rand[A])(f: A => B): Rand[B] = 
    rng => 
        val (a, rng2) = s(rng)
        (f(a), rng2)


def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = 
    rng => 
        val (a, rng2) = ra(rng)
        val (b, rng3) = rb(rng2)
        (f(a, b), rng3)

def both[A, B](ra: Rand[A, rb: Rand[B]): Rand[(A, B)] = 
    map2(ra, rb)((_, _))

def randIntDouble: Rand[(Int, Double)] = 
    both(int, double)

def randDoubleInt: Rand[(Double, Int)] = 
    both(double, int)


val int : Rand[Int] = _.nextInt

def double(rng: RNG): (Double, RNG) = 
    map(_.nextInt)(number => {
        val positiveNumber = if number < 0 then -(number + 1) else number
        val normalized = positiveNumber.toDouble / (Int.MaxValue.toDouble + 1)
        normalized
    })(rng)

@main def runPurelyFunctionalState(): Unit =
    val rng = SimpleRNG(42)
    val nextIntRand: Rand[Int] = _.nextInt
    val doubledRand = map(nextIntRand)(_ * 2)

    val (randomInt, _) = nextIntRand(rng)
    val (constant, _) = unit(7)(rng)
    val (doubledInt, _) = doubledRand(rng)

    println(s"nextInt: $randomInt")
    println(s"unit(7): $constant")
    println(s"mapped nextInt (* 2): $doubledInt")

    val (doubleValue, _) = double(rng)
    println(s"double: $doubleValue")
