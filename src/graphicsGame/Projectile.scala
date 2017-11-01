package graphicsGame

class Projectile(private var _x: Double, private var _y: Double, val level: Level,
    private var movingUp: Boolean, private var movingDown: Boolean, private var movingLeft: Boolean, private var movingRight: Boolean) extends Entity {
  def cx = _x
  def cy = _y
  def isEnemy = false
  def isProjectile = true
  def isPlayer = false
  
  def update(delay: Double): Unit = {
    if (movingUp) {
      if (level.maze.isClear(_x, _y - delay, .5, .5)) _y -= delay*2
    }
    if (movingDown) {
      if (level.maze.isClear(_x, _y + delay, .5, .5)) _y += delay*2
    }
    if (movingLeft) {
      if (level.maze.isClear(_x - delay, _y, .5, .5)) _x -= delay*2
    }
    if (movingRight) {
      if (level.maze.isClear(_x + delay, _y, .5, .5)) _x += delay*2
    }
  }
  
}