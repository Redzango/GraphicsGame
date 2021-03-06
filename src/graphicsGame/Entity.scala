package graphicsGame

/**
 *the trait for all entities in the game
 */
trait Entity {
  def cx: Double
  def cy: Double
  def width: Double = 1.25
  def height: Double = 1.25
  def isEnemy: Boolean
  def isProjectile: Boolean
  def isPlayer: Boolean
  def isalive: Boolean
  var score = 0
  def getScore(i:Int):Unit = score += i

  
  def intersect(other: Entity): Boolean = {
      val intersectX = (cx - other.cx).abs < (width + other.width) / 2
      val intersectY = (cy - other.cy).abs < (height + other.height) / 2
      intersectX && intersectY
    }

  def update(delay: Double): Unit
  def buildPassable : PassableEntity 


}

object Entity {

}