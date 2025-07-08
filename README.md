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

**What do Patterns Match?**

- A _constructor pattern_ `C(p_1, ..., p_n)` matches all the values of type `C` (of a subtype) that have been constructed with arguments matching the patterns `p_1, ..., p_n` .
- A _variable pattern_ `x` matches any value and binds the name of the variable to this value.
- A _constant pattern_ `c` matches values that are equal to `c` (in the sense of `==` )


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


## Packages and Imports

### **📦 Packages**

A package is a namespace for organising classes, traits, objects and functions.

```scala

package myapp.utils

class Logger:
  def log(msg: String): Unit = println(s"LOG: $msg")

// Nested packages
package myapp:
  package utils:
    class Logger:
      def log(msg: String): Unit = println(msg)
// ---------------------------------------------------
// accessing a class from another package
import myapp.utils.Logger

@main def run = 
  val logger = new Logger()
  logger.log("Hello from another package!")

```


###  **📥 Imports**

```scala

// basic
import scala.collection.mutable.ArrayBuffer

// multiple members
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

// wildcard import
import scala.collection.mutable._

// renaming on import
import scala.collection.mutable.{Map => MutableMap}

// Hiding on import
import scala.collection.mutable.{Map => _, _} // imports everything except Map


// importing within a function
@main def run =
  import scala.util.Random
  println(Random.nextInt())cccc

```


###  **✅ Best Practices**

| **Practice**                  | **Reason**                                              |
| ----------------------------- | ------------------------------------------------------- |
| Use specific imports          | Avoids namespace pollution                              |
| Use package objects sparingly | Great for utility functions but can clutter             |
| Prefer top-level definitions  | Nested packages are neat but harder to navigate         |
| Use aliases for clarity       | Especially helpful when importing similarly named types |


## Extended Backus-Naur Form

```
| denotes alternative
[...] an option (0 or 1),
{...} a repetition (0 or more)
```


### Types
```
Type         = SimpleType | FunctionType
FunctionType = SimpleType '=>' Type
             | '(' [Types] ')' '=>' Type
SimpleType   = Ident
Types        = Type {',' Type}
```

## Scala's Class Hierarchies

```markdown

scala.Any
├── scala.AnyVal
│   ├── scala.Double
│   ├── scala.Float
│   ├── scala.Long
│   ├── scala.Int
│   ├── scala.Short
│   ├── scala.Byte
│   ├── scala.Unit
│   ├── scala.Boolean
│   └── scala.Char
├── scala.AnyRef (java.lang.Object)
│   ├── java.lang.String
│   ├── scala.Iterable
│   │   └── scala.Seq
│   │       └── scala.List
│   └── (other Java/Scala classes)
└── scala.Nothing (subtype of all types)
```

## Classes

```scala
class Person(val name: String, val age: Int): // this is the primary constructor
  def greet(): String = s"Hello, my name is $name and I am $age years old."
```

- Class parameters are passed to the **primary constructor**.
- val → read-only member
- var → mutable member
- Without val/var, parameters are not accessible outside the class.
- **NOTE** : Statements inside a class body are executed at object construction time.

### Access Modifiers

- public (default): accessible from anywhere.
- private: accessible only within the class.
- protected: accessible within the class and subclasses.
- private\[this]: strict private; inaccessible even from other instances.

```scala
class BankAccount(private var balance: Double):
  def deposit(amount: Double): Unit = balance += amount
  def getBalance: Double = balance
```

### Inheritance

- Scala supports single inheritance using extends.

```scala
class Animal:
  def speak(): Unit = println("generic sound")

class Dog extends Animal:
  override def speak(): Unit = println("woof")
```

- Use override **explicitly** when overriding methods.
- Subclasses must call superclass constructor:

```scala
class Animal(val species: String)

class Dog(name: String) extends Animal("Dog")
```

#### Protected Members

- Accessible to subclasses, but not outside the class hierarchy.

```scala
class Animal:
  protected def sound: String = "some sound"

class Cat extends Animal:
  def makeSound(): Unit = println(sound) // ✅
```


### Auxiliary Constructors

```scala

class Person(val name: String, val age: Int):
  def this(name: String) = this(name, 0)

```

####  **🧠 Scala’s Philosophy**

Scala **favors companion object apply** over secondary constructors because:

- It keeps the **constructor logic decoupled** from the class
- It enables more **functional patterns**
- It makes the API **cleaner** and more **flexible**

```scala

class Person(val name: String, val age: Int)

object Person:
  def apply(name: String): Person = new Person(name, 0)

```


### Extension Methods

An **extension method** allows you to “add” a method to an existing type _without modifying the original class_. Think of it like:

- **Monkey-patching** in Python (but type-safe and scoped).
- Similar to **category theory’s functors**, you’re lifting behavior over types.
- Enriching libraries and types you don't own

**NOTE**
1. Extensions can only add new members, not override existing ones.
2. Extensions cannot refer to other class members via _this_ .

Example

- Add  `isEven`  to `Int`

```scala
extension (x: Int)
  def isEven: Boolean = x % 2 == 0

println(4.isEven) // true
println(5.isEven) // false


/**
SYNTAX: 
extension (receiverType)
  def methodName(args...): ReturnType = { ... }
*/

extension (s: String)
  def shout: String = s.toUpperCase + "!"
  def isLong: Boolean = s.length > 10

"hello".shout  // "HELLO!"
"world".isLong // false

```


Extension methods are **only available where they are in scope**. That means:
- You can define them in a file and import them.
- You can place them inside objects or packages.

### Class Hierarchies

#### Abstract Classes

```scala

abstract class Animal:
  def speak(): String       // abstract method
  val hasTail: Boolean      // abstract val

  def move(): String = "I move around" // concrete method


abstract class Shape(val name: String):
  def area: Double

class Circle(radius: Double) extends Shape("Circle"):
  def area: Double = Math.PI * radius * radius

```

👉 _**Rule of thumb**_

- Use **abstract class** when you need a base class with constructor parameters or want tight control.
- Use **trait** when you’re mixing in modular behaviors (like interfaces with implementations).

#### Traits

Traits are similar to interfaces in Java. Traits can contain
- Abstract methods and fields
- concrete methods and fields

> unlike Java, the primary tool of decomposition in Scala is not classes, but traits.

```scala

trait HasTail: 
	def tailColor: String def wagTail() = println("Tail is wagging") 
	def stopTail() = println("Tail is stopped")

trait HasLegs:
	def numLegs: Int
	def walk(): Unit
	def stop() = println("Stopped walking")


class IrishSetter(name: String) extends HasLegs, HasTails:
	val numLegs = 4
	val tailColor = "Red"
	def walk() = println("I'm walking")
	override def toString = s"$name is a Dog"
	
```

Scala 3 has traits with parameters

```scala

trait Pet(name: String): 
	def greeting: String 
	def age: Int 
	override def toString = s"My name is $name, I say $greeting, and I’m $age" 
	
class Dog(name: String, var age: Int) extends Pet(name): 
	val greeting = "Woof" 
	val d = Dog("Fido", 1)

```

Traits are more flexible to compose—you can mix in multiple traits, but only extend one class—and **should be preferred to classes and abstract classes most of the time**. The rule of thumb is to use classes whenever you want to create instances of a particular type, and traits when you want to decompose and reuse behaviour.

- _However Abstract Classes should be used when compatibility with Java code is required_


##### SubTyping

where a given trait is required a subtype of the trait can be used instead

```scala

import scala.collection.mutable.ArrayBuffer

trait Pet:
	val name: String

class Cat(val name: String) extends Pet
class Dog(val name: String) extends Pet

val dog = Dog("Harry")
val cat = Cat("Sally")

val animals = ArrayBuffer.empty[Pet]
animals.append(dog)
animals.append(cat)
animals.foreach(pet => println(pet.name))

```


####  `apply`, `unapply` and Extractor Objects 

#####  **🧵**   **`apply`** **Method**

Scala uses apply as a convention to **call objects like functions**.

> If you define an `apply(...)` method, you can call the object using `()` as if it were a function.

```scala

object Adder:
  def apply(x: Int, y: Int): Int = x + y

val sum = Adder(2, 3)  // Equivalent to Adder.apply(2, 3)

```

###### Multiple `apply` overloads

```scala
object MathUtil:
  def apply(x: Int): Int = x * x
  def apply(x: Int, y: Int): Int = x + y

MathUtil(3)       // 9
MathUtil(3, 4)    // 7
```



###  Companion Objects and Companion Classes

A **companion object** is an object that shares the same name as a class (or trait) and is defined **in the same file**.

- The class is called the **companion class**.
- They can **access each other’s private members**.
- A companion object is often used for:

    - Factory methods (`apply`)
    - Constants and static members        
    - Helper functions
    - Pattern matching (`unapply`)

####  **✅ Why Companion Object?**

Scala has **no static members** (like Java’s static), so companion objects act as a place for:
- Static-like members
- Factory methods
- Constants
- Pattern matching extractors

```scala
class Person(val name: String, val age: Int)

object Person:
  def apply(name: String): Person = new Person(name, 0)

val p = Person("Alice")  // Calls the apply method → Person("Alice", 0)
```


#####  **🔐 Access to Private Members**

```scala
class Counter(private var value: Int):
  def current = value

object Counter:
  def increment(c: Counter): Unit =
    c.value += 1   // ✅ allowed — accessing private member

```


## Enums

Define a type that consists of a finite set of named values.

```scala
enum CrustSize: 
	case Small, Medium, Large

enum CrustType: 
	case Thin, Thick, Regular

enum Topping: 
	case Cheese, Pepperoni, BlackOlives, GreenOlives, Onions


// to use them 
import CrustSize.* 

val currentCrustSize = Small

// if/then
if currentCrustSize == Large then
  println("You get a prize!")

// match
currentCrustSize match
  case Small => println("small")
  case Medium => println("medium")
  case Large => println("large")

```

##### Parametrized Enums

```scala
enum Color(val rgb: Int):
	case Red   extends Color(0xFF0000)
	case Green extends Color(0x00FF00)
	case Blue  extends Color(0x0000FF)

```

##### Enums with fields and methods

```scala

enum Planet(mass: Double, radius: Double): 
	private final val G = 6.67300E-11 
	def surfaceGravity = G * mass / (radius * radius) 
	def surfaceWeight(otherMass: Double) = otherMass * surfaceGravity 
	
	case Mercury extends Planet(3.303e+23, 2.4397e6) 
	case Earth extends Planet(5.976e+24, 6.37814e6)

```

##### Methods defined for enums

- The values of an enum correspond to unique integers. The integer associated with an enum value is returned by its `ordinal` method (not for parameterized):

```scala
val red = Color.Red
println(red.ordinal) // val res0: Int = 0
```

- The companion object of an enum also defines three utility methods. 
	- The `valueOf` method obtains an enum value by its name.
	- The `values` method returns all enum values defined in an enumeration in an immutable `Array`.
	- The `fromOrdinal` method obtains an enum value from its ordinal (`Int`) value.

```scala

Color.valueOf("Blue") // val res0: Color = Blue
Color.values // val res1: Array[Color] = Array(Red, Green, Blue)
Color.fromOrdinal(0) // val res2: Color = Red
```


####  🧠  Calling Functions Without Parameters — `def name` vs. `def name()`

In Scala, the way you **define** and **invoke** functions without parameters conveys **semantic intent**. There are two syntactically similar but semantically distinct forms:

---

#####  🔹 1. Parameterless Methods (`def name = ...`)

```scala
class Rational(x: Int, y: Int):
  def numer = x
  def denom = y
```

- ✅ Called as: `r.numer`
- ❌ Calling `r.numer()` will cause a **compiler error**: method `numer` in class Rational does not take parameters

**Use when:**
- The method is **pure** (no side effects)
- The result is conceptually a **field** or **property**
- You want to signal **referential transparency

##### **🔹 2. Empty Parentheses Methods (** **def name() = ...)**

```scala
class Rational(x: Int, y: Int):
  def numer(): Int = x
  def denom(): Int = y
```

- ✅ Called as: r.numer()
- You may **optionally** omit the () in some usages, but it’s **intended** to be called with parentheses

**Use when:**
- The method has **side effects**
- The result may **change** each time it’s called
- You want to **signal an action**, not a value


## Case Classes

- Used to model immutable data structures.
- Useful in functional programming scenarios
	- supports functinoal decomposition

```scala

case class Person(name: String, relation: String)
val christina = Person("Christina", "niece")

// christina.name = "Fred" // error
// Case classes can be used as patterns 
christina match case Person(n, r) => println("name is " + n) 
// `equals` and `hashCode` methods generated for you 
val hannah = Person("Hannah", "niece") 
christina == hannah // false 
// `toString` method 
println(christina) // Person(Christina,niece) 
// built-in `copy` method 
case class BaseballTeam(name: String, lastWorldSeriesWin: Int) 
val cubs1908 = BaseballTeam("Chicago Cubs", 1908) 
val cubs2016 = cubs1908.copy(lastWorldSeriesWin = 2016) 
// result: // cubs2016: BaseballTeam = BaseballTeam(Chicago Cubs,2016)
```

- Scala compiler automatically generates many helpful methods
	- `unapply` method. Therefore _pattern matching_ on case classes is allowed `case Person(n, r) => ...`
	- A `copy` method
	- `equals` and `hashCode` : allows use instances of case classes in `Map` 
	- A `toString` method

> A case class has a built-in extractor object with `unapply` generated for free

```scala

case class Person(name: String, age: Int)

val p = Person("Alice", 30)

p match
  case Person(n, a) => println(s"$n is $a years old")

```

### Case Objects

- Similar to objects but for `case class` es rather than `class` es. 
- Case objects are useful when you need to pass immutable messages around.


## **📦** Generics

### Generic Classes

allow abstraction over types

```scala

class Stack[A]:
	private var elements: List[A] = Nil
	def push(x: A): Unit = 
		elements = x :: elements
	def peek: A = elements.head
	def pop(): A = 
		val currentTop = peek
		elements = elements.tail
		currentTop

// usage

val stack = Stack[Int]
stack.push(1)
stack.push(2)


// subtypes
class Fruit
class Apple extends Fruit
class Banana extends Fruit

val stack  = Stack[Fruit]
val apple  = Apple()
val banana = Banana()

stack.push(apple)
stack.push(banana)


// multiple types
class Pair[A, B](first: A, second: B):
  def swap: Pair[B, A] = Pair(second, first)

```

### Generic Methods

```scala

def identity[A](x: A): A = x

val i = identity(10)      // Inferred: Int
val s = identity("hi")    // Inferred: String

```

### Type Inference

```scala

// Generic Methods
def double[A](x: A): (A, A) = (x, x)
val r = double(3.14)  // Inferred: (Double, Double)

// Generic classes
class Container[A](val value: A)
val c = Container("hello")  // Inferred: Container[String]

// Limitations
def toList[A](a: A, b: A): List[A] = List(a, b)

val ok = toList(1, 2)              // Inferred: List[Int]
val fail = toList(1, "hi")         // Error: cannot infer A
val fixed = toList[Any](1, "hi")   // OK

```



## Lists

- Recursive structures
- immutable - the elements of a list cannot be changed
- homogenous

All lists are constructed from :
- the empty list Nil, and
- the construction operation ::
	- `x::xs` gives a new list with the first element `x`, followed by the elements of `xs`


```scala

val fruit = "apple"::("oranges" :: ("pears" :: Nil))
val nums  = 1 :: (2 :: (3 :: (4 :: Nil)))

val empty = Nil

// :: is right associative
1 :: 2 :: 3 :: Nil // 1 :: (2 :: (3 :: Nil))


// Pattern Matching on List
def describe[A](lst: List[A]): String = lst match
  case Nil           => "Empty list"
  case head :: Nil   => s"Single element: $head"
  case x :: y :: Nil => s"Two elements: $x and $y"
  case x :: xs       => s"Head: $x, Tail: $xs"


val list = List(1, 2, 3, 4)

list match
  case a :: b :: _ => println(s"First two elements: $a and $b")
  case _ => println("List too short")

// match arbitrary length list
list match
  case Nil => println("Empty")
  case h1 :: h2 :: tail => println(s"First: $h1, Second: $h2, Rest: $tail")

list match
  case _ :: second :: _ => println(s"Second element: $second")
  case _ => println("Too short")

```


###  **✅ 8. Tips**

- Always end a chain with `Nil` : `1 :: 2 :: 3 :: Nil` 
- Use `::` in patterns to deconstruct lists
- Avoid `.head`, `.tail` if you can match with `::`  instead (safer and more idiomatic)


## Subtyping and Generics Interaction

Two principal forms of polymorphism:
 1. Subtyping
 2. Generics

Their interactions - 
1. Bounds
2. Variance

## Type Bounds

> Type bounds in Scala provide a mechanism to constrain the types that can be used for type parameters in generic classes or functions. They enhance type safety and allow for more precise control over generics

**Purpose**
- type bounds allow you to **_specify a relationship between a type parameter and another type_** . This ensures that the type parameter can only be instantiated with types that conform to these specified bounds.

### Upper Bounds

- **Syntax**: An upper bound is declared using the syntax `S <: T`. (_S is subtype of T_)
- **Meaning** : This means that the type parameter `S` can only be instantiated with types that are subtypes of `T`. In essence, `S` must conform to `T`'s interface.
- **Example** : `assertAllPos`:
	- Consider a method `assertAllPos` which takes a set and returns that same set if all its elements are positive, otherwise it throws an exception.
	- A less precise type signature might be `assertAllPos(s: IntSet): IntSet`. While this works, it loses information about the specific subtype of `IntSet` that was passed in.
	- By using an upper bound, `def assertAllPos[S <: InSet](r: S): S`, the type signature becomes more precise.
	- Here, `S` is the type parameter, and `IntSet` is its upper bound, meaning `S` must be a subtype of `IntSet`.
	- This type signature expresses that whatever specific subtype of `IntSet` you put in, you get out that same specific subtype1.
	- For instance, if you pass an `Empty` set (which is a subtype of `IntSet`), `S` can be instantiated to `Empty`, and an `Empty` set is returned. Similarly, if a `NonEmpty` set is passed, `S` is `NonEmpty`, and a `NonEmpty` set is returned.
	- This retains the specific type information which would be lost if the return type was simply InSet

### Lower Bounds

- **Syntax** : A lower bound is declared using the syntax `S :> T`. (_S is supertype of T_)
- **Meaning** : This means that the type parameter `S` can only be instantiated with types that are supertypes of `T` (or `T` itself). In other words, `T` must be a subtype of `S`.
	- **Example** : `S :> NonEmpty` would mean that `S` could be `NonEmpty`, `IntSet` (if `IntSet` is a supertype of `NonEmpty`), `AnyRef`, or `Any`. 

### Mixed Bounds

- **Syntax** : It is possible to combine both a lower and an upper bound for single type parameter. The lower bound comes first.
- **Example** : `S :> NonEmpty <: IntSet` 
- **Meaning** : This specifies that `S` must be a supertype of `NonEmpty` and a subtype of `IntSet`.


## Variance

> Variance in Scala's type system describes how subtyping relationships between complex types behave when their type parameters have a subtyping relationship. It addresses the question : 
> _if type_ `A` _is a subtype of type_ `B` _(e.g Apple is subtype of Fruit) then how does_ `C[A]` _relate to_ `C[B]` _for a generic type_ `C[T]` ?

**Definition**

Say `C[T]` is a parametrized type and `A, B` are types such that `A <: B`. 
In general there are _three_ possible relationships between `C[A]` and `C[B]`: 

`C[A] <: C[B]`  - C is _covariant_
`C[A] >: C[B]`  - C is _contravariant_ 
neither `C[A]` nor `C[B]` is a subtype of the other - C is _nonvariant_ 


There are three main types

### 1. Covariance

- **Definition** : A type `C[T]` is covariant if, given `A <: B`, then `C[A]` is a subtype of `C[B]` . This means the  subtyping relationship _"varies in the same direction"_ as the type parameter.
- **Syntax**

```scala
class C[+T] ... // you declare the parametrized type i.e. C here , to be covariant ..
trait List[+T] ...
```

- **Intuition** : Intuitively, if you have a collection of more specific items, it can be treated as a collection of more general items. For instance, a list of `Apple` objects can be used where a list of `Fruit` objects is expected, as `List[Apple]` is a subtype of `List[Fruit]` if `Apple` is a subtype of `Fruit` .
### 2. Contraviariance

- **Definition** : A type `C[T]` is contravariant if, given `A <: B`, then `C[B]` is a subtype of `C[A]`. This means the subtyping relationship "varies in the other direction"
- **Syntax** : 

```scala
class C[-T]
```

- **Example** : 

### 3. Non - variance

- **Definition** : A type `C[T]` is non-variant (or invariant) if neither `C[A]` nor `C[B]` is a subtype of the other, given `A <: B`.

- **Syntax** : No annotation is used before the type parameter, for example, `class C[T]`.

- **Example** : Scala arrays (`Array[T]`) are non-variant. This design decision in Scala prevents the runtime errors seen with Java arrays by making assignments like `Array[NonEmpty] = Array[InSet]` a compile-time type error


### Liskov Substitution Principle (LSP) 

- guiding principle for sound subtyping.
> _if_ `A` _is a subtype of_ `B` _, then **everything** one can do with a value of type_ `B` _(the supertype) should also be possible with a value of type_ `A`.
>  `A` is "better than" `B` because it can fullfill every role `B` plays, potentially with more functionality.


### Typing Rules for Functions

> If `A2 <: A1` and `B1 <: B2` then
> `A1 => B1 <: A2 => B2`

So functions are _contravariant_  (or invariant) 

in their argument type(s) and _covariant_ in their result type.
So the underlying function trait would like

```scala
package scala
trait Function1[-T, +U]:
	def apply(x: T): U
```

Roughly,
- _covariant_ type parameters can only appear in method results.
- _contravariant_ type parameters can only appear in method parameters.
- _nonvariant_ type parameters can appear anywhere

The precise rules are a bit more involved.

##  **📦** Pair and Tuples

- A **tuple** is a finite, ordered collection of elements.
- Each element **can have a different type**.
- Scala supports tuples from **Tuple1 to Tuple22**.
- A tuple of two elements is called a **Pair** (Tuple2).

```scala

val pair = ("hello", 42)     // Tuple2[String, Int]
println(pair._1)             // "hello"
println(pair._2)             // 42

val t3 = ("Scala", 3.0, true) // Tuple3[String, Double, Boolean]
println(t3._1)                // "Scala"
println(t3._2)                // 3.0
println(t3._3)                // true
```


***Common Use Cases***

```scala

// Returning multiple values from functions
def stats(xs: List[Int]): (Int, Int) = 
  (xs.min, xs.max)

val (minVal, maxVal) = stats(List(1, 2, 3, 4))

// zipping lists
val names = List("Alice", "Bob")
val scores = List(85, 90)
val paired = names.zip(scores)
// List(("Alice", 85), ("Bob", 90))



```


***Accessing Elements***

```scala
// preferred way
val person = ("Ada", 1815)
val (name, birthYear) = person

// or 
println(s"${person._1}") // Ada

```


## Collection Hierarchy

```
                    Iterable
                   /    |    \
                Seq    Set   Map
               /   \
           List   Vector

Array (not part of this tree, comes from Java)
```


## Sequence

In Scala, a **Sequence** (trait Seq) is an **ordered collection** of elements. It forms a key part of the Scala Collections hierarchy.

1. **Ordered**: 
	- Elements are stored in a specific order (the order they were inserted).
2. **Indexed or Linear**: 
	- Some Seq types support fast indexing (like `Vector`, `Array`, `IndexedSeq`), others are optimized for head/tail operations (like List).
3.  **Immutable by default**: 
	- Seq in the `scala.collection.immutable` package is immutable unless stated otherwise.


| **Type**   | **Use When You Need**                                    |
| ---------- | -------------------------------------------------------- |
| `List`     | Fast prepend (`O(1)`), functional programming idioms     |
| `Vector`   | General-purpose immutable collection, fast random access |
| `Array`    | Java interop, performance-critical code                  |
| `Range`    | Represent a sequence of evenly spaced numbers            |
| `LazyList` | Potentially infinite sequences                           |

### Sequence Methods and Operators

#### Construction and Access

| **Method**       | **Description**           | **Example**       |
| ---------------- | ------------------------- | ----------------- |
| `apply(i)`       | Access element at index i | `seq(2)`          |
| `length / size`  | Number of elements        | `seq.length`      |
| `isEmpty`        | Check if empty            | `seq.isEmpty`     |
| `nonEmpty`       | Check if not empty        | `seq.nonEmpty`    |
| `head`           | First element             | `seq.head`        |
| `last`           | Last element              | `seq.last`        |
| `init`           | All but last              | `seq.init`        |
| `tail`           | All but first             | `seq.tail`        |
| `indexOf(elem)`  | Index of first occurrence | `seq.indexOf(3)`  |
| `contains(elem)` | Whether element exists    | `seq.contains(5)` |

#### Transformations

| **Method**     | **Description**                    | **Example**                            |
| -------------- | ---------------------------------- | -------------------------------------- |
| `map(f)`       | Apply function to each element     | `seq.map(_ * 2)`                       |
| `flatMap(f)`   | Apply function and flatten         | `seq.flatMap(x => List(x, -x))`        |
| `collect(f)`   | Pattern match and transform        | `seq.collect { case x if x > 0 => x }` |
| `filter(p)`    | Keep elements satisfying predicate | `seq.filter(_ % 2 == 0)`               |
| `filterNot(p)` | Drop elements satisfying predicate | `seq.filterNot(_.isEmpty)`             |
| `distinct`     | Remove duplicates                  | `seq.distinct`                         |
| `reverse`      | Reverse the sequence               | `seq.reverse`                          |
| `sorted`       | Sort (requires Ordering)           | `seq.sorted`                           |
| `sortBy(f)`    | Sort using a key                   | `seq.sortBy(_.length)`                 |
| `sortWith(p)`  | Custom comparator                  | `seq.sortWith(_ > _)`                  |

#### Aggregations and Reductions

| **Method**         | **Description**                    | **Example**               |
| ------------------ | ---------------------------------- | ------------------------- |
| `foldLeft(z)(op)`  | Left fold                          | `seq.foldLeft(0)(_ + _)`  |
| `foldRight(z)(op)` | Right fold                         | `seq.foldRight(0)(_ + _)` |
| `reduceLeft(op)`   | Reduce left without initial value  | `seq.reduceLeft(_ + _)`   |
| `reduceRight(op)`  | Reduce right without initial value | `seq.reduceRight(_ + _)`  |
| `sum`              | Sum of elements (numeric types)    | `seq.sum`                 |
| `product`          | Product of elements                | `seq.product`             |
| `min / max`        | Smallest / Largest element         | `seq.min, seq.max`        |
| `mkString(sep)`    | Join elements as string            | `seq.mkString(", ")`      |

#### Grouping and Sliding

| **Method**     | **Description**                     | **Example**                 |
| -------------- | ----------------------------------- | --------------------------- |
| `groupBy(f)`   | Group elements by key               | `seq.groupBy(_.length)`     |
| `sliding(n)`   | Returns iterator of sliding windows | `seq.sliding(2).toList`     |
| `grouped(n)`   | Fixed-size chunks                   | `seq.grouped(3).toList`     |
| `partition(p)` | Split into 2 groups by predicate    | `seq.partition(_ % 2 == 0)` |
| `splitAt(n)`   | Splits into 2 at index n            | `seq.splitAt(3)`            |
| `span(p)`      | Prefix satisfying predicate + rest  | `seq.span(_ < 4)`           |


#### Searching

| **Method**      | **Description**                    | **Example**              |
| --------------- | ---------------------------------- | ------------------------ |
| `find(p)`       | First element satisfying predicate | `seq.find(_ > 3)`        |
| `exists(p)`     | If any element satisfies predicate | `seq.exists(_ % 2 == 0)` |
| `forall(p)`     | If all elements satisfy predicate  | `seq.forall(_ > 0)`      |
| `indexWhere(p)` | Index of first matching element    | `seq.indexWhere(_ > 5)`  |

#### Modification (non-mutating)

| **Method**              | **Description**                     | **Example**                     |
| ----------------------- | ----------------------------------- | ------------------------------- |
| `:+`                    | Append an element                   | `seq :+ 10`                     |
| `+:`                    | Prepend an element                  | `0 +: seq`                      |
| `++`                    | Concatenate sequences               | `seq ++ Seq(6, 7)`              |
| `updated(i, v)`         | Replace element at index i          | `seq.updated(1, 42)`            |
| `patch(from, patch, n)` | Replace slice with another sequence | `seq.patch(2, Seq(99, 100), 1)` |
| `drop(n)`               | Drop first n elements               | `seq.drop(2)`                   |
| `dropRight(n)`          | Drop last n elements                | `seq.dropRight(2)`              |
| `take(n)`               | Take first n elements               | `seq.take(3)`                   |
| `slice(from, until)`    | Slice from from to until - 1        | `seq.slice(1, 4)`               |

## Lists

```scala
// Construction
val fruits = List("Apple", "Orange", "Banana")
val nums   = 1 :: 2 :: Nil // Nil is required here

// Decomposition
fruits.head   // "Apple"
nums.tail    // 2 :: Nil
nums.isEmpty // false

nums match
	case x :: y :: _ => x + y // 3


// creating new lists:
xs ++ ys // concatenation
xs.reverse 
cs.updated(n, x) // the new list containing the same element as xs, except at index n where it contains x.

// Finding elements
xs.indexOf(x) // The index of the first element in xs equal to x, or -1 if x does not appear in xs

xs.contains(x) // same as xs.indexOf(x) >= 0

```

### **📋 List Methods**

| **Method**           | **Description**                                                                        |
| -------------------- | -------------------------------------------------------------------------------------- |
| xs.length            | The number of elements of xs.                                                          |
| xs.last              | The list’s last element, exception if xs is empty.                                     |
| xs.init              | A list consisting of all elements of xs except the last one, exception if xs is empty. |
| xs.take(n)           | A list consisting of the first n elements of xs, or xs itself if it is shorter than n. |
| xs.drop(n)           | The rest of the collection after taking first n elements.                              |
| xs(n) or xs.apply(n) | The element of xs at index n.                                                          |

**Important Program**

```scala
// List[Any]: List(List(1,1), 2, List(3, 4)) flattened to List(1,1,2,3,4)
// Notice how List[Any] can be used to create heterogeneous lists
def flatten(xs: Any) : List[Any] = xs match
  case Nil => Nil
  case y :: ys => flatten(y) ++ flatten(ys)
  case _ => xs :: Nil // interesting NOTE
```

### ***Higher Order List Functions***

#### 1. `map`

```scala
// pedagogical example
extension [T](xs: List[T])
	def map(p: T => T): List[T] = xs match
		case Nil     => Nil
		case x :: xs1 => p(x) :: map(xs1)

```

####  2. `filter`

```scala

// pedagogical implementation

extension [T](xs: List[T])
	def filter(p: T => Boolean): List[T] = xs match
		case Nil    => xs
		case x :: xs1 => if p(x) then x :: xs1.filter(p) else xs1.filter(p)

// you can make calls like
xs.filter(x => x > 0)
```

#####  🔁 Variations of `filter` in Scala

| Method            | Behavior                                                  |
| ----------------- | --------------------------------------------------------- |
| `xs.filterNot(p)` | Keeps elements that do **not** satisfy predicate `p`      |
| `xs.partition(p)` | Splits into `(xs.filter(p), xs.filterNot(p))` efficiently |
| `xs.takeWhile(p)` | Longest prefix of elements satisfying `p`                 |
| `xs.dropWhile(p)` | Drops prefix satisfying `p`, returns the rest             |
| `xs.span(p)`      | Tuple of `(takeWhile(p), dropWhile(p))`, in one traversal |

#### Reduction of Lists

##### `reduceLeft`

Reduces a collection to a single value by applying a binary function **left to right**.

```scala
// Evaluation order
List(a1, a2, a3).reduceLeft(op) 
// expands to: op(op(a1, a2), a3)

// Example - Sum
List(1, 2, 3, 4).reduceLeft(_ + _)  // 10

// Example - Concatenation
List("Scala", "is", "fun").reduceLeft(_ + " " + _)  // "Scala is fun"

// CAUTION - empty list not supported
List().reduceLeft(_ + _)  // ❌ UnsupportedOperationException

```

***prefer using `foldLeft` over `reduceLeft`***

##### `foldLeft`

```scala
def foldLeft[B](z: B)(op: (B, A) => B): B

```

The binary operator is of the form `(accumulator, listElement)` and is expected to return the same type as `accumulator` .


Applies a binary operator to a start value and all elements of this list, going left to right.

- Takes an explicit **initial value** `z`
- Safe on **empty collections**
- Can return a different type than the collection’s element type


```scala

List(1, 2, 3).foldLeft(0)(_ + _) 
// ((0 + 1) + 2) + 3 = 6

List[Int]().foldLeft(0)(_ + _) 
// 0 (safe)


```

##### `foldLeft` vs `foldRight`

They are equivalent when the operation is:
- **Associative**: (a op b) op c == a op (b op c)
- **Commutative**: a op b == b op a

Even though ***order of evaluation differs***
- `foldLeft: (((z op x1) op x2) op x3)`
- `foldRight: (x1 op (x2 op (x3 op z)))`

They give different results when the `op` is non-commutative

 **📌 Efficiency:**
- foldLeft: ✅ Tail-recursive (efficient)
- foldRight: ❌ Not stack-safe on List 

Also the signatures of op in both are different

**`foldLeft`**

```scala
def foldLeft[B](z: B)(op: (B, A) => B): B
```
- Traverses: **left to right**
- Function op: accumulator comes **first**
- Order: (acc, elem) => ...

**`foldRight`**

```scala
def foldRight[B](z: B)(op: (A, B) => B): B
```

- Traverses: **right to left**
- Function op: element comes **first**
- Order: (elem, acc) => ...

***Example***

```scala
val nums = List(1, 2, 3)

// using foldLeft
nums.foldLeft(0)((acc, x) => acc + x)
// (0 + 1) => 1
// (1 + 2) => 3
// (3 + 3) => 6

/**
Step-by-step:

foldLeft(0)
  => ((0 + 1) = 1)
  => ((1 + 2) = 3)
  => ((3 + 3) = 6)

Visual:

        +
      /   \
     +     3
   /   \
  +     2
 / \
0   1
*/



// using foldRight
nums.foldRight(0)((x, acc) => x + acc)
// (3 + 0) => 3
// (2 + 3) => 5
// (1 + 5) => 6

/**

Step-by-step:

foldRight(0)
  => ((3 + 0) = 3)
  => ((2 + 3) = 5)
  => ((1 + 5) = 6)

Visual:

        +
      /   \
     1     +
         /   \
        2     +
            /   \
           3     0

*/

```

## Vectors

Vectors in Scala are **immutable**, **indexed sequences** that provide **efficient random access** and **update operations**. They are a good choice when you want:

- Fast read/update access to elements by index (O(log n) or better)
- Immutability (structural sharing on updates)
- Append and prepend operations that are _relatively_ fast (amortized O(1))

It is as an **immutable general-purpose alternative to Array or List**, designed for **performance and safe functional programming**.

```scala

val v1 = Vector(1, 2, 3)
val v2 = v1.updated(1, 42)

println(v1)  // Vector(1, 2, 3)
println(v2)  // Vector(1, 42, 3)

/**
✅ v1 remains unchanged
✅ v2 is a new vector
✅ Internally, only the branch with index 1 is copied/modified — rest is shared
*/

```

### Vector Internals

> Even though it seems you can “change” elements (e.g., updated), Scala does **not** mutate the vector in-place. Instead, it **creates a new vector** that shares most of the underlying structure with the original, using a **tree-like structure** internally (32-ary tree).


Scala Vector is implemented as a **wide shallow tree** (usually 32-ary). Each node holds up to 32 elements or child nodes.

- **Accessing an element by index** is O(log₃₂ n) ≈ constant for most practical sizes.
- **Updating** is efficient because only a small part of the tree is rebuilt.

_You can think of a Vector like a **shelf of books** with 32 compartments. Instead of copying the whole shelf when you update a book, you **copy only the one compartment**, and leave the rest unchanged — a very functional way of pretending you’re mutating, without side effects._

This structure gives it a good **tradeoff between indexing speed and immutability**.

### Common Operations for Vectors

#### Creation

```scala
val v = Vector(1, 2, 3)
val empty = Vector.empty[Int]
val filled = Vector.fill(5)("x")
val tabulated = Vector.tabulate(5)(n => n * n)  // Vector(0, 1, 4, 9, 16)
```

#### Modification (return new Vector)

```scala
val v1 = Vector(1, 2, 3)

// Append and Prepend
// NOTE colon always points to the sequence
val v2 = v1 :+ 4           // Vector(1, 2, 3, 4) // APPEND
val v3 = 0 +: v1           // Vector(0, 1, 2, 3) // PREPEND

// Update element
val v4 = v1.updated(1, 99) // Vector(1, 99, 3)

// Concatenation
val v5 = v1 ++ Vector(4, 5)
```

#### Query & Access

```scala
v1(0)             // Access by index
v1.head, v1.tail  // Like lists
v1.take(2)        // Vector(1, 2)
v1.drop(1)        // Vector(2, 3)
v1.indexOf(3)     // 2
v1.contains(2)    // true
```

#### Transformations

```scala

v1.map(_ * 2)         // Vector(2, 4, 6)
v1.filter(_ % 2 == 1) // Vector(1, 3)
v1.reverse            // Vector(3, 2, 1)
v1.zipWithIndex       // Vector((1,0), (2,1), (3,2))

```

#### Aggregations

```scala
v1.sum                // 6
v1.foldLeft(0)(_ + _) // 6
v1.reduce(_ max _)    // 3
```

#### Comparison with Other Sequences

| **Feature**        | List     | Vector             | Array           |
| ------------------ | -------- | ------------------ | --------------- |
| Immutable          | ✅        | ✅                  | ❌ (mutable)     |
| Fast head          | ✅ (O(1)) | ❌ (O(log n))       | ✅               |
| Fast random access | ❌ (O(n)) | ✅ (O(log n))       | ✅ (O(1))        |
| Fast append        | ❌ (O(n)) | ✅ (amortized O(1)) | ❌ (copy needed) |
| Memory efficiency  | ✅        | ✅                  | ✅               |

## Range

In Scala, a **Range** is a special kind of **sequence** that represents a series of evenly spaced integers. It is **immutable**, **efficient**, and commonly used for iteration, loops, and indexing.

### Syntax and Operators

Scala provides three key operators to define ranges:

| **Operator** | **Meaning**         | **Example**  | **Result**    |
| ------------ | ------------------- | ------------ | ------------- |
| to           | Inclusive           | 1 to 5       | 1, 2, 3, 4, 5 |
| until        | Exclusive           | 1 until 5    | 1, 2, 3, 4    |
| by           | Step (stride) value | 1 to 10 by 3 | 1, 4, 7, 10   |

**Examples Explained**

```scala
val r: Range = 1 until 5
/*
- Produces: Range(1, 2, 3, 4)
- 5 is excluded → until is exclusive
*/

val s: Range = 1 to 5
// - Produces: Range(1, 2, 3, 4, 5) 
// - 5 is included → to is inclusive

val t: 1 to 10 by 3
// - Produces: Range(1, 4, 7, 10)  
// - Starts at 1, increments by 3 until ≤ 10

val u: 6 to 1 by -2
// - Produces: Range(6, 4, 2) 
// - Steps downward with negative step size

```


### Use Cases

**`for` loops**

```scala
for (i <- 1 to 5) println(i)
```

**generating test data**

```scala
val testIds = (100 to 110 by 2).toList
```

**Repeated actions**

```scala
(1 to 3).foreach(_ => println("Hi!"))
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


## Higher Order Function

- Functions that take other functions as parameter and/or returns a function then they are called _Higher Order Functions_
```scala

// sum is a higher order function
def sum(f: Int => Int, a: Int, b: Int): Int = 
	if a > b then 0
	else f(a) + sum(f, a+1, b)

val sumOfInts = sum(x => x, 1, 10) // 55

// sum returning a function
def sum(f: Int => Int): (a: Int, b: Int) => Int = 
	def sumF(a: Int, b: Int): Int = 
		if a > b then 0
		else f(a) + sumF(a+1, b)
	sumF

// so now we can call
val sumOfInts = sum(x=>x)(1, 10) // 55

// a short hand for the above function definition in scala is
def sum(f: Int => Int)(a: Int, b: Int): Int = 
	if a > b then 0 else f(a) + sum(f)(a+1, b)

```


### Anonymous Function

- `(x: Int) => x * x * x` , `(x: Int, y: Int) => x + y` 
- they are syntactic sugar
- **Function Types** : The type `A => B` is the type of a function that takes an argument of type A and returns a result of type B.

## Currying 

- Functions with Multiple Parameter lists

```scala
def f(ps1)..(psn-1)(psn) = E

// is equivalent to 
def f = (ps1 => (ps2 => ..(psn => E))) // Currying
```

- **Note** : Generally Function application associates to the left.

```scala
// assuming sum : (Int => Int) : (Int, Int) => Int
sum(cube)(1, 10) == (sum(cube)) (1, 10)
```

- **Note**: Function Types associate to the right

```scala
Int => Int => Int
// is same as
Int => (Int => Int)


(Int => Int) => (Int, Int) => Int
// is same as
(Int => Int) => ((Int, Int) => Int)

```

- Currying leads to greater code reusability
- For example

```scala
def translator(locale: String)(text: String): String = 
	// some translation logic

val inFrench = translator("fr")
println(inFrench("Hello"))

```

- Important program
```scala
/**
 * write a product function that calculates the product of the values of a function for the points on
 * a given interval
 *
 * write factoral in terms of product
 *
 * write a more general function, which generalises both sum and product
 *
 * */


def product(f: Int => Int)(a: Int, b: Int): Int = 
  if a > b then 1
  else f(a) * product(f)(a+1, b)


def factorial(n: Int): Int =
  product(x => x)(1, n)


def mapReduce(f: Int => Int)(combine: (Int, Int) => Int, identity: Int)(a: Int, b: Int): Int = 
  if a > b then identity
  else combine(f(a), mapReduce(f)(combine, identity)(a+1, b))

// NOTE. assining function to function
// Thanks to currying!!!
def sum(f: Int => Int) = mapReduce(f)((x: Int, y: Int) => x + y, 0)


@main def run(a: Int, b: Int): Unit = 
  println(product(x => x)(a, b))
  println(s"factorial : ${factorial(b)}")
  println(s"sum : ${mapReduce(x=>x)(_ + _, 0)(a, b)}")
  println(s"product : ${mapReduce(x=>x)(_ * _, 1)(a, b)}")

```