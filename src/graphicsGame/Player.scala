package graphicsGame

import java.rmi.server.UnicastRemoteObject

class Player(private var _x: Double, private var _y: Double, val level: Level,
    private var movingUp: Boolean, private var movingDown: Boolean, private var movingLeft: Boolean, private var movingRight: Boolean,
    private var fireing: Boolean, private var lastMove: Int, private var alive: Boolean, var score: Int) extends UnicastRemoteObject with Entity with RemotePlayer {
  def cx = _x
  def cy = _y
  def isEnemy = false
  def isProjectile = false
  def isPlayer = true
  def isDie = alive = false
  def isalive = alive
  var health = 3
  var invincable = false
  var invtick = 0
  def scor = score

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
    if (fireing) {
      if (firetick % 23 == 0) {
        fire()
        firetick = 0
      }
      firetick += 1
    }
    for (i <- level.entities) {
      if (i.isEnemy) {
        if (intersect(i) && !invincable && invtick==0) {
          println("hit")
          health -= 1
          if(health<1) alive = false
          invincable = true
          invtick = 50
        } 
      }
    }
    if (invtick>0) invtick -=1
    if (invtick == 0) invincable = false
  }

  var firetick = 0

  def fire(): Unit = {
    if (fireing) {
      if (lastMove == 0) level.+=(new Projectile(cx, cy, level, true, false, false, false, true))
      if (lastMove == 1) level.+=(new Projectile(cx, cy, level, false, true, false, false, true))
      if (lastMove == 2) level.+=(new Projectile(cx, cy, level, false, false, true, false, true))
      if (lastMove == 3) level.+=(new Projectile(cx, cy, level, false, false, false, true, true))
    }
  }

  def buildPassable: PassableEntity = new PassableEntity(0, _x, _y, width, height)

  def chLastMove(dir: Int): Unit = {
    if (dir == 0) lastMove = 0
    if (dir == 1) lastMove = 1
    if (dir == 2) lastMove = 2
    if (dir == 3) lastMove = 3
  }

  def firePressed(): Unit = fireing = true

  def fireReleased(): Unit = {
    fireing = false
    firetick = 0
  }

  def moveUpPressed(): Unit = movingUp = true

  def moveDownPressed(): Unit = movingDown = true

  def moveLeftPressed(): Unit = movingLeft = true

  def moveRightPressed(): Unit = movingRight = true

  def moveUpReleased(): Unit = movingUp = false

  def moveDownReleased(): Unit = movingDown = false

  def moveLeftReleased(): Unit = movingLeft = false

  def moveRightReleased(): Unit = movingRight = false
}

@remote trait RemotePlayer {
  def cx: Double
  def cy: Double
  def firePressed(): Unit
  def fireReleased(): Unit
  def moveUpPressed(): Unit
  def moveDownPressed(): Unit
  def moveLeftPressed(): Unit
  def moveRightPressed(): Unit
  def moveUpReleased(): Unit
  def moveDownReleased(): Unit
  def moveLeftReleased(): Unit
  def moveRightReleased(): Unit
  def isDie: Unit
  def chLastMove(dir: Int): Unit
  def scor: Int
}