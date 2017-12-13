package graphicsGame
/**
 * the class for the Generator, a type of unkillable enemy that spawns other enemys at an increasing rate
 */
class Generator(private var _x: Double, private var _y: Double, val level: Level, var alive: Boolean) extends Entity {
  def cx = _y
  def cy = _x
  def isEnemy = true
  def isProjectile = false
  def isPlayer = false
  def isalive = alive
  def givePoints(p: Int): Unit = {
    for (i <- level.entities) {
      if (i.isPlayer) {
        i.getScore(p)
      }
    }
  }
  var dificulty = 0
  var num = 100-dificulty
  var tick = 0
  def update(delay: Double): Unit = {
    if (tick % num == 0 && alive) {
      if (dificulty<94) dificulty += 1
      val enemy = new Enemy(_x, _y - 1, level, true, 0)
      level.+=(enemy)
      if (dificulty > 50){
      val bomber = new Bomber(_x, _y - 1, level, true, 0)
      level.+=(bomber)
      }
    }
    tick = tick + 1
    for (i <- level.entities) {
      if (i.isProjectile) {
        if (intersect(i)) {
          givePoints(5)
          alive = false
        }
      }
    }
  }
  def buildPassable: PassableEntity = new PassableEntity(4, _x, _y, width, height)
}