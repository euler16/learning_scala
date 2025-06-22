### Links 
1. [Nerd's Academy's Youtube video Functional Programming in Scala](https://www.youtube.com/watch?v=BDU7bae68-Q)
2. [Official Scala3 Book](https://docs.scala-lang.org/scala3/book/scala-features.html)
3. [Metals Plugin for Neovim](https://github.com/scalameta/nvim-metals)


# Scala Programming Language

## Control Structures

### if/else
- expressionss not statements
```scala
val x = if y < 0 then 10 else 11 // the type of this expression is Int

// the type of this expression would be Unit (same as println)
if x < 0 then
	println("Negative")
else if x == 0 then
	println("zero")
else
	println("positive")
```


### While Loop

```scala
val x = 0
while x < 10 do
	println(x)
	x += 1 // mutability?

```

### For Loops

```scala
val ints = Seq(1, 3, 4)
for i <- ints do println(i)


for 
	i <- 1 to 5
	j <- 10 to 13
	if i%2 == 0 // guards
	if j%2 == 1
do
	println(i, j)


// using with maps
val states = Map( "AK" -> "Alaska", "AL" -> "Alabama", "AR" -> "Arizona" )
for 
	(abbrev, fullName) <- states 
do 
	println(s"$abbrev: $fullName")

```

#### For expressions

- use yield instead of do
```scala
val x = for i <- 1 to 5 yield i*i

// this is equivalent to the following map
val x = (1 to 5).map(i => i*i)
```

### Match 

- is an expression
- Use match when
	- You have a **finite set of cases** to branch on
	- You’re working with **Algebraic Data Types (ADTs)** like sealed traits or Option, Either, etc.
	- You want **destructuring** (e.g., breaking apart a tuple or list)
	- You want to **handle types differently** via pattern matching
- can pattern match not just values but also:
	1. Types
	2. Case classes
	3. Tuples
	4. Lists
	5. Option values
	6. Custom extractors
```scala
// basic
val code = 404
val msg = code match
  case 200 => "OK"
  case 404 => "Not Found"
  case _   => "Unknown"

// multiple possible matches
val evenOrOdd = i match
	case 1 | 3 | 5 | 7 | 9  => println("odd")
	case 2 | 4 | 6 | 8 | 10 => println("even")
	case _ => println("some other")

// on tuples
val t = (1, "apple")
val result = t match
  case (1, fruit) => s"One $fruit"
  case (_, "banana") => "Some banana"
  case _ => "Unknown combo"

// on types
def process(x: Any): String = x match
  case i: Int    => s"Integer: $i"
  case s: String => s"String: $s"
  case _         => "Unknown type"


/* uppercase -> matches with a variable in scope,
   lowercase -> variable binding for default case
*/
val N = 42
val i = 42
i match
  case N => println("Matched N!")
  case n => println(s"Default: $n")

// if guards
// if guards in case clause
i match
	case a if 0 to 9 contains a => println(s"0-9 range: $a")
	case b if 10 to 19 contains b => println(s"10-19 range: $b")
	case _ => println("No match")


// Matching types
def describe(x: Any): String = x match
  case i: Int        => s"An integer: $i"
  case s: String     => s"A string of length ${s.length}"
  case l: List[_]    => s"A list with ${l.length} elements"
  case _             => "Something unknown"

```
  
 - case something
    - If something is lowercase → treat it as a **new variable to bind**    
    - If Something is uppercase → treat it as an **existing value or class**

- Match expression as the body of a method
```scala
def pattern(x: Matchable): String = x match

	// constant patterns
	case 0 => "zero"
	case true => "true"
	case "hello" => "you said 'hello'"
	case Nil => "an empty list"

	// sequence patterns
	case List(0, _, _) => "a three element list with 0 as the first element"
	case List(1, _*) => "list , starts with 1, has any number of elements"
	case Vector(1, _*) => "vector starts with 1 has any number of elements"

	// tuple pattern
	case (a, b) => s"got $a and $b"
	//constructor pattern
	case Person(first, "Alexander") => s"Alexander, first name = $first"
	case Dog("Zeus") => "found a dog named Zeus"

	// type test pattern
	case s: String => s"got a string: $s"
	case i: Int => s"got an int: $i" 
	case f: Float => s"got a float: $f" 
	case a: Array[Int] => s"array of int: ${a.mkString(",")}" 
	case as: Array[String] => s"string array: ${as.mkString(",")}"

	case d: Dog => s"dog: ${d.name}" 
	case list: List[?] => s"got a List: $list" 
	case m: Map[?, ?] => m.toString 
	
	// the default wildcard pattern 
	case _ => "Unknown"


```



# Functional Programming Concepts

## Referential Transparency

- A linguistic construction is called _referentially transparent_ when for any expression built from it, replacing a subexpression with another one that denotes the same value does not change the value of the expression.
- This is a property of expressions in general not just functions.
- In simpler words , an expression is referentially transparent then in _any_ program, the expression can be replaced by its result without changing the meaning of the program.

#### Example of non-referential transparency
```scala
// not referentially transparent

val x  = new StringBuilder("Hello") // Hello
val y  = x.append(", World") // Hello, World
val r1 = y.toString // Hello, World
val r2 = y.toString // Hello, World

// reason -> substitutiton

val x  = new StringBuilder("Hello")
val r1 = x.append(", World").toString // Hello, World
val r2 = x.append(", World").toString // Hello, World, World

```

#### Example of referential transparency
```scala
val x  = "Hello, World"
val r1 = x.reverse // dlroW ,olleH
val r2 = x.reverse // dlroW ,olleH

// reason -> substitution

val r1 = "Hello, World".reverse // dlroW ,olleH
val r2 = "Hello, World".reverse // dlroW ,olleH

```

## _Pure_ Functions

- a function is pure if calling it with referentially transparent arguments is also referentially transparent
- Referential transparency forces the invariant that everything a function does is represented by the value that it returns according to the result type of the function.


## Substitution Model of Evaluation

- Does every expression reduce to a value (in a finite number of steps?
	- No `def loop: Int = loop`

### Evaluation Strategies

#### 1. Call by Value

- **mechanism**: function arguments are **evaluated to their values _before_ being passed** to the function
- **advantage**: every function argument is evaluated only once.
- **predictable** : when side effects involved call by value is  **more predictable** .

```scala
// by default call by value
def square(x: Double): Double = x * x
def sumOfSquares(x: Double, y: Double): Double = square(x) + square(y)

sumOfSquares(3, 2 + 2)
/**
Evaluation
sumOfSquares(3, 2 + 2)
sumOfSquares(3, 4)
square(3) + square(4)
3*3 + 4*4
25
**/

```

#### 2. Call by Name

- **mechanism** : function arguments are not evaluated before the function is applied. Instead, the parameter is replaced by the expression itself, and this expression is evaluated each time the corresponding parameter is referenced within function's body.
- **advantage** : if parameter completely unused during function's evaluation, its argument expression is never evaluated at all. Example : boolean short circuiting.

```scala
def square(x: Double): Double = x * x
// special syntax for call by name. space between : and => required
def sumOfSquares(x: Double, y: => Double): Double = square(x) + square(y)

/**
Evaluation
sumOfSquares(3, 2 + 2)
square(3) + square(2 + 2)
3*3 + (2+2)*(2+2)
9 + 16
**/

```

#### Comparison between Call by Value vs Call by Name

- Expressions composed of pure functions and if both evaluation strategies terminate, they will always reduce to the same final value
- if call by value terminates => call by name evaluation will also terminate
- vice versa not true

```scala

def loop: Int = loop
def first(x: Int, y: Int): Int = x
first(1, loop) // will go into infinite loop (loop defined above)


// however
def first(x: Int, y: => Int): Int = x
first(1, loop) // will not go into infinite loop
```

- An example where the difference between call by name and call by value becomes important

```scala
def and(x: Boolean, y: => Boolean): Boolean = if x == true then y else false
// to enable shorcircuiting we have to go by call by name
```


## Tail Recursion

- Tail recursion is a form of recursion where the **recursive call is the last operation** in a function. This allows the Scala compiler to **optimize** it into a loop internally, preventing stack overflow errors.

- In a typical recursive function, each call adds a new frame to the call stack. But with tail-recursive functions, no additional stack frame is needed.

```scala
def factorial(n: Int): BigInt =
  def go(n: Int, acc: BigInt): BigInt =
    if n == 0 then acc
    else go(n - 1, acc * n)

  go(n, 1)

// tail recursive fibonacci
def fibonacci(n: Int): Int = 
  def go(i: Int, a: Int, b: Int): Int =
    if i == n then a
    else go(i + 1, b, a + b)

  go(0, 0, 1)
```


