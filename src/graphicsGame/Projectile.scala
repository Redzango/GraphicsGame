package graphicsGame

class Projectile(private var _x: Double, private var _y: Double, val level: Level,
    private var movingUp: Boolean, private var movingDown: Boolean, private var movingLeft: Boolean, private var movingRight: Boolean, private var alive: Boolean) extends Entity {
  def cx = _x
  def cy = _y
  def isalive = alive
  def isEnemy = false
  def isProjectile = true
  def isPlayer = false
  def buildPassable: PassableEntity = new PassableEntity(3, _x, _y, width, height)
  var prex = _x.toDouble
  var prey = _y.toDouble

  def update(delay: Double): Unit = {
    if (movingUp) {
      if (level.maze.isClear(_x, _y - delay, .5, .5)) _y -= delay * 2
    } else if (movingDown) {
      if (level.maze.isClear(_x, _y + delay, .5, .5)) _y += delay * 2
    } else if (movingLeft) {
      if (level.maze.isClear(_x - delay, _y, .5, .5)) _x -= delay * 2
    } else if (movingRight) {
      if (level.maze.isClear(_x + delay, _y, .5, .5)) _x += delay * 2
    } 
    if (prex == _x && prey == _y) alive = false
    prex = _x
    prey = _y
  }

}