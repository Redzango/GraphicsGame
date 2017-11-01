package graphicsGame

class Level(val maze: Maze, private var _entities: Seq[Entity]) {
  def entities = _entities
  
  def +=(e:Entity):Unit ={
    _entities = _entities:+(e)
  }
  
  def -=(e:Entity):Unit ={
    _entities = _entities.filterNot(_ == e)
  }
  
  def updateAll(delay: Double): Unit={
    _entities.foreach(_.update(delay))
    //stillhere
  }
}
