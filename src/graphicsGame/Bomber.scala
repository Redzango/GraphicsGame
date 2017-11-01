package graphicsGame

class Bomber(private var _x: Double, private var _y: Double, val level: Level, private var dead: Boolean, private var dir: Int) extends Entity {
  def cx = _x
  def cy = _y
  def isEnemy = true
  def isProjectile = false
  def isPlayer = false
  def setDir(i: Int): Unit = {
    dir = i
  }
  var tick = 0

  def update(delay: Double): Unit = {
    if (tick % 10 == 0) {
      var index = level.entities.indexOf(Player)
      var up = level.maze.bfs(_x.toInt, _y.toInt - 1, level.entities(index).cx.toInt, level.entities(0).cy.toInt)
      var down = level.maze.bfs(_x.toInt, _y.toInt + 1, level.entities(0).cx.toInt, level.entities(0).cy.toInt)
      var left = level.maze.bfs(_x.toInt - 1, _y.toInt, level.entities(0).cx.toInt, level.entities(0).cy.toInt)
      var right = level.maze.bfs(_x.toInt + 1, _y.toInt, level.entities(0).cx.toInt, level.entities(0).cy.toInt)
      var arr = Array(up, down, left, right)
      dir = arr.indexOf(arr.min)

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
      tick = 0
    }
  }
}
