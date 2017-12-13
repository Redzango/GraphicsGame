package graphicsGame
/**
 * queue trait for queues
 */
trait MyQueue[A] {
  def enqueue(a: A): Unit
  def dequeue(): A
  def peek: A
  def isEmpty: Boolean
}