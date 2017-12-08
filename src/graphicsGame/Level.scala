package graphicsGame

class Level(val maze: Maze, private var _entities: Seq[Entity]) {
  def entities = _entities

  def +=(e: Entity): Unit = {
    _entities = _entities :+ (e)
  }

  def -=(e: Entity): Unit = {
    _entities = _entities.filterNot(_ == e)
  }

  def updateAll(delay: Double): Unit = {
    _entities.foreach(_.update(delay))
    for (i <- _entities)
      if (!i.isalive) -=(i)
  }

  val offsets = Array((-1, 0), (1, 0), (0, -1), (0, 1))

  def bfs(sx: Int, sy: Int, ex: Int, ey: Int, width: Double, height: Double): Int = {
    if (!maze.isClear(sx, sy, width, height)) 10000
    else {
      val q = new ArrayQueue[(Int, Int, Int)]()
      q.enqueue((sx, sy, 0))
      val visited = collection.mutable.Set[(Int, Int)]()
      visited += sx -> sy
      while (!q.isEmpty) {
        val (x, y, steps) = q.dequeue()
        for ((dx, dy) <- offsets) {
          val (nx, ny) = (x + dx, y + dy)
          if (nx == ex && ny == ey) return steps + 1
          if (!visited.contains(nx -> ny) && nx >= 0 && nx < maze.width &&
            ny >= 0 && ny < maze.height && maze.isClear(nx, ny, width, height)) {
            q.enqueue((nx, ny, steps + 1))
            visited += nx -> ny
          }
        }
      }
      10000
    }
  }

  def buildPassable: PassableLevel = new PassableLevel(maze, entities.map(_.buildPassable))
}
