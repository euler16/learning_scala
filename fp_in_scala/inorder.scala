import scala.annotation.tailrec 

enum Tree[+A]: 
  case Empty
  case Node(
      value : A, 
      left : Tree[A],
      right : Tree[A]
  )

def _inorder[A](tree : Tree[A]): Unit = tree match 
  case Tree.Empty => print(" ")
  case Tree.Node(value, left, right) => {
    inorder(left)
    print(value)
    inorder(right)
  }

def inorder[A](tree : Tree[A]): List[A] = 
  tree match 
    case Tree.Empty => Nil
    case Tree.Node(value, left, right) => inorder(left) ++ List(value) ++ inorder(right)

def inorder_tr[A](tree : Tree[A]) : List[A] = 
  
  @tailrec 
  def loop(
    current : Tree[A],
    stack : List[(A, Tree[A])], // this tells to visit the parent of the current value and then its right subtree
    accumulator : List[A]
  ) : List[A] = 
    current match 
      case Tree.Empty => {
        stack match 
          case Nil => accumulator.reverse 
          case (value, right)::remainingStack => 
            loop(
              current = right,
              stack = remainingStack,
              accumulator = value :: accumulator
            )
      }
      case Tree.Node(value, left, right) =>
        loop(
          current = left
          stack = (value, right) :: stack,
          accumulator = accumulator 
        )


@main def printInOrder : Unit = 
  val tree: Tree[Int] =
  Tree.Node(
    value = 10,
    left =
      Tree.Node(
        value = 5,
        left = Tree.Empty,
        right = Tree.Empty
      ),
    right =
      Tree.Node(
        value = 15,
        left = Tree.Empty,
        right = Tree.Empty
      )
  )

  inorder(tree).foreach(println)
