package graphicsGame
/**
 * first type of enemy that moves randomly
 */
class Enemy(private var _x: Double, private var _y: Double, val level: Level, private var alive: Boolean, private var dir: Int) extends Entity {
  def cx = _x
  def cy = _y
  var tick = 0
  def isEnemy = true
  def isProjectile = false
  def isPlayer = false
  def isalive = alive
  def buildPassable: PassableEntity = new PassableEntity(1, _x, _y, width, height)
  def givePoints(p:Int):Unit ={
    for (i <- level.entities) {
      if(i.isPlayer){
        i.getScore(p)
      }
    }
  }
  def update(delay: Double): Unit = {
    tick += 1
    var num = scala.util.Random.nextInt(4)
    if (tick % 40 == 0) dir = num
    if (dir == 0) {
      if (level.maze.isClear(_x, _y - delay, 1.25, 1.25)) _y -= delay
    }
    if (dir == 1) {
      if (level.maze.isClear(_x, _y + delay, 1.25, 1.25)) _y += delay
    }
    if (dir == 2) {
      if (level.maze.isClear(_x - delay, _y, 1.25, 1.25)) _x -= delay
    }
    if (dir == 3) {
      if (level.maze.isClear(_x + delay, _y, 1.25, 1.25)) _x += delay
    }
    for (i <- level.entities) {
      if (i.isProjectile) {
        if (intersect(i)) {
          givePoints(5)
          alive = false
        }
      }
    }
  }
}
