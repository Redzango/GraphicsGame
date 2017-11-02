package graphicsGame

import java.rmi.server.UnicastRemoteObject

class Player(private var _x: Double, private var _y: Double, val level: Level,
    private var movingUp: Boolean, private var movingDown: Boolean, private var movingLeft: Boolean, private var movingRight: Boolean,
    private var fireingUp: Boolean, private var fireingDown: Boolean, private var fireingLeft: Boolean, private var fireingRight: Boolean, 
    private var lastMove: Int, private var alive: Boolean) extends UnicastRemoteObject with Entity{
  def cx = _x
  def cy = _y
  def isEnemy = false
  def isProjectile = false
  def isPlayer = true
  def isDie = alive = false
  def isalive:Boolean = alive

  def update(delay: Double): Unit = {
    if (movingUp) {
      if (level.maze.isClear(_x, _y - delay, 1.25, 1.25)) _y -= delay
    }
    if (movingDown) {
      if (level.maze.isClear(_x, _y + delay, 1.25, 1.25)) _y += delay
    }
    if (movingLeft) {
      if (level.maze.isClear(_x - delay, _y, 1.25, 1.25)) _x -= delay
    }
    if (movingRight) {
      if (level.maze.isClear(_x + delay, _y, 1.25, 1.25)) _x += delay
    }
  }
  def buildPassable : PassableEntity = new PassableEntity(0,_x,_y,width,height)

  def isFireingUp = fireingUp
  def isFireingDown = fireingDown
  def isFireingLeft = fireingLeft
  def isFireingRight = fireingRight

  def firePressed(): Unit = {
    if (lastMove == 0) fireUpPressed
    else if (lastMove == 1) fireDownPressed
    else if (lastMove == 2) fireLeftPressed
    else if (lastMove == 3) fireRightPressed
  }
  def fireReleased(): Unit = {
    fireUpReleased
    fireDownReleased
    fireLeftReleased
    fireRightReleased
  }

  def moveUpPressed(): Unit = {
    movingUp = true
    lastMove = 0
  }
  def fireUpPressed(): Unit = fireingUp = true

  def moveDownPressed(): Unit = {
    lastMove = 1
    movingDown = true
  }
  def fireDownPressed(): Unit = fireingDown = true

  def moveLeftPressed(): Unit = {
    lastMove = 2
    movingLeft = true
  }
  def fireLeftPressed(): Unit = fireingLeft = true

  def moveRightPressed(): Unit = {
    lastMove = 3
    movingRight = true
  }
  def fireRightPressed(): Unit = fireingRight = true

  def moveUpReleased(): Unit = movingUp = false
  def fireUpReleased(): Unit = fireingUp = false

  def moveDownReleased(): Unit = movingDown = false
  def fireDownReleased(): Unit = fireingDown = false

  def moveLeftReleased(): Unit = movingLeft = false
  def fireLeftReleased(): Unit = fireingLeft = false

  def moveRightReleased(): Unit = movingRight = false
  def fireRightReleased(): Unit = fireingRight = false

}