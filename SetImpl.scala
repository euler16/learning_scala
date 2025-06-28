/**
 * write a class for set with the following operations
 * incl(x) -> adds an element x to the set
 * contains(x): Boolean -> checks if the element belongs to the set
 */

abstract class Set[A]:
  def incl(x: A): Set[A]
  def contains(x: A): Boolean
  def union(other: Set[A]): Set[A]


object Empty[A] extends Set[A]:
  def contains(x: Int): Boolean = false
  def incl(x: A): Set[A] = NonEmpty(x, Empty(), Empty())
  def union(other: Set[A]): Set[A] = other


class NonEmpty[A](elem: A, left: Set[A], right: Set[A]) extends Set[A]:
  
  def value: A = elem
  def leftChild: Set[A] = left
  def rightChild: Set[A] = right

  def contains(x: A): Boolean = 
    if x < elem then left.contains(x)
    else if x > elem then right.contains(x)
    else true

  def incl(x: A): NonEmpty[A] = 
    if x < elem then NonEmpty(elem, left.incl(x), right)
    else if x > elem then NonEmpty(elem, left, right.incl(x))
    else this

  def union(other: Set[A]): Set[A] =
    left.union(right).union(other).incl(elem)

