package graphicsGame

import scala.reflect.ClassTag
/**
 * Classs for an Array Queue and its methods
 */
class ArrayQueue[A: ClassTag] extends MyQueue[A] {
  private var data = new Array[A](10)
  private var front = 0
  private var back = 0

  def enqueue(a: A): Unit = {
    if ((back + 1) % data.length == front) {
      val temp = new Array[A](data.length * 2)
      for (i <- 0 until data.length - 1) temp(i) = data((i + front) % data.length)
      front = 0
      back = data.length - 1
      data = temp
    }
    data(back) = a
    back = (back + 1) % data.length
  }
  def dequeue(): A = {
    val ret = data(front)
    front = (front + 1) % data.length
    ret
  }
  def peek: A = data(front)
  def isEmpty: Boolean = front == back
}