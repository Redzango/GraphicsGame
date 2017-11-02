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
  def buildPassable : PassableEntity = new PassableEntity(2,_x,_y,width,height)
  var tick = 0

  def update(delay: Double): Unit = {
    if (tick % 40 == 0) {
      var index = level.entities.head
      var up = level.maze.bfs(_x.toInt, _y.toInt - 2, index.cx.toInt, index.cy.toInt)
      var down = level.maze.bfs(_x.toInt, _y.toInt + 2, index.cx.toInt, index.cy.toInt)
      var left = level.maze.bfs(_x.toInt - 2, _y.toInt, index.cx.toInt, index.cy.toInt)
      var right = level.maze.bfs(_x.toInt + 2, _y.toInt, index.cx.toInt, index.cy.toInt)
      var arr = Array(up, down, left, right)
      dir = arr.indexOf(arr.min)
     /* if(up<=down && up<=left && up<=right) dir = 0
      if(left<=down && left<=up && left<=right) dir = 2
      if(down<=up && down<=left && down<=right) dir = 1
      if(right<=down && right<=left && right<=up) dir = 3 */
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
