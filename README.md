### Links 
1. [Nerd's Academy's Youtube video Functional Programming in Scala](https://www.youtube.com/watch?v=BDU7bae68-Q)
2. [Official Scala3 Book](https://docs.scala-lang.org/scala3/book/scala-features.html)
3. [Metals Plugin for Neovim](https://github.com/scalameta/nvim-metals)

# Referential Transparency

- A linguistic construction is called _referentially transparent_ when for any expression built from it, replacing a subexpression with another one that denotes the same value does not change the value of the expression.
- This is a property of expressions in general not just functions.
- In simpler words , an expression is referentially transparent then in _any_ program, the expression can be replaced by its result without changing the meaning of the program.

#### Example of non-referential transparency
```
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
```
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


