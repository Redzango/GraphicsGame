package graphicsGame
/**
 * class for the Bomber, the seconds type of enemy that uses a bfs shortest path
 */
class Bomber(private var _x: Double, private var _y: Double, val level: Level, private var alive: Boolean, private var dir: Int) extends Entity {
  def cx = _x
  def cy = _y
  def isEnemy = true
  def isProjectile = false
  def isPlayer = false
  def isalive = alive
  def setDir(i: Int): Unit = dir = i
  def givePoints(p: Int): Unit = {
    for (i <- level.entities) {
      if (i.isPlayer) {
        i.getScore(p)
      }
    }
  }

  def buildPassable: PassableEntity = new PassableEntity(2, _x, _y, width, height)

  def update(delay: Double): Unit = {
    if (!level.entities.filter(_.isPlayer).isEmpty) {
      var index = level.entities.filter(_.isPlayer)(0)
      var up = level.bfs(_x.toInt, _y.toInt - 1, index.cx.toInt, index.cy.toInt, width, height)
      var down = level.bfs(_x.toInt, _y.toInt + 1, index.cx.toInt, index.cy.toInt, width, height)
      var left = level.bfs(_x.toInt - 1, _y.toInt, index.cx.toInt, index.cy.toInt, width, height)
      var right = level.bfs(_x.toInt + 1, _y.toInt, index.cx.toInt, index.cy.toInt, width, height)
      var arr = Array(up, down, left, right)
      dir = arr.indexOf(arr.min)
      if (dir == 0) _y -= delay * .6
      if (dir == 1) _y += delay * .6
      if (dir == 2) _x -= delay * .6
      if (dir == 3) _x += delay * .6
    }
    for (i <- level.entities) {
      if (i.isProjectile) {
        if (intersect(i)) {
          givePoints(50)
          alive = false
        }
      }
    }
  }
}
