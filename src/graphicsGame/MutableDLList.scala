package graphicsGame

/**
 * class for a mutable double linked list
 */
class MutableDLList[A] {
  private class Node(var prev: Node, var value: A, var next: Node)
  private var default: A = _
  private val end = new Node(null, default, null)
  end.next = end
  end.prev = end
  private var size = 0
  
  def apply(i: Int): A = {
    if(i < 0 || i >= size) throw new IndexOutOfBoundsException(s"Index $i out of $size")
    var rover = end.next
    for(_ <- 0 until i) rover = rover.next
    rover.value
  }
  
  def update(i: Int, a: A): Unit = {
    if(i < 0 || i >= size) throw new IndexOutOfBoundsException(s"Index $i out of $size")
    var rover = end.next
    for(_ <- 0 until i) rover = rover.next
    rover.value = a
  }
  
  def insert(i: Int, a: A): Unit = {
    if(i < 0 || i > size) throw new IndexOutOfBoundsException(s"Index $i out of $size")
    var rover = end.next
    for(_ <- 0 until i) rover = rover.next
    val n = new Node(rover.prev, a, rover)
    rover.prev.next = n
    rover.prev = n
    size += 1
  }
  
  def remove(i: Int): A = {
    if(i < 0 || i > size) throw new IndexOutOfBoundsException(s"Index $i out of $size")
    var rover = end.next
    for(_ <- 0 until i) rover = rover.next
    rover.prev.next = rover.next
    rover.next.prev = rover.prev
    size -= 1
    rover.value
  }
  
  def length: Int = size
  
  
  def add(a: A): Unit = {
    size += 1
    val n = new Node(end.prev, a, end)
    end.prev.next = n
    end.prev = n
  }
  
  def map[B](f: A => B): MutableDLList[B] = {
    val ret = new MutableDLList[B]()
    var rover = end.next
    while(rover!=end) {
      ret.add(f(rover.value))
    }
    ret
  }
}