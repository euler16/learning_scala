/**
 *  IMMUTABLE_LINKED_LIST
 *
 *  two building blocks
 *  1. Nil  - The empty list 
 *  2. Cons - a cell containing an element and the remainder of the list (Cons stands for construct)
 *
 *  Example : 
 *    List(1, 2, 3)
 *
 *            cons 
 *           /     \ 
 *          1        cons
 *                  /    \
 *                 2      cons
 *                        /   \
 *                       3     NIL
 *
 *
*/


//abstract class LIST[A]:
trait LIST[T]:
  def isEmpty: Boolean
  def head: T 
  def tail: LIST[T]

/**
 *  A list is either
 *  - an empty list Nil() or 
 *  - a list Cons(x, xs) consisting of a head element x and a tail list xs 
 *
 */

class CONS[T](val head: T, val tail: LIST[T]) extends LIST[T]: 
  def isEmpty = false


class NIL[T] extends LIST[T]:
  def isEmpty = true
  def head    = throw new NoSuchElementException("Nil.head")
  def tail    = throw new NoSuchElementException("Nil.tail")


// function that returns the n'th element of the list
// elements are numbered from 0
// if index is outside the range from 0 up  the length of the list - 1,
// IndexOutOfBoundsException should be thrown
def nth[T](xs: LIST[T], n: Int): T = 
  if xs.isEmpty then throw IndexOutOfBoundsException()
  else if n == 0 then xs.head
  else nth(xs.tail, n-1)


@main def run = 
  println(nth(CONS(1, CONS(2, CONS(3, NIL()))), 2))


