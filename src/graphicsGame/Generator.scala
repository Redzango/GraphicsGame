package graphicsGame

class Generator(private var _x: Double, private var _y: Double, val level: Level, var alive: Boolean) extends Entity {
  def cx = _y
  def cy = _x
  def isEnemy = true
  def isProjectile = false
  def isPlayer = false
  def isalive = alive

  var tick = 0
  def update(delay: Double): Unit = {
    if (tick % 100 == 0 && alive) {
      val enemy = new Enemy(_x, _y - 1, level, true, 0)
      level.+=(enemy)
    }
    tick = tick + 1
    for (i <- level.entities) {
      if (i.isProjectile) {
        if (intersect(i)) {
          alive = false
          println("gen hit")
        }
      }
    }
  }
  def buildPassable: PassableEntity = new PassableEntity(4, _x, _y, width, height)
}