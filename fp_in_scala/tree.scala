object TreeExercises:
  enum Tree[+A]:
    case Leaf(value: A)
    case Branch(left: Tree[A], right: Tree[A])

    def depth: Int = 
      this match 
