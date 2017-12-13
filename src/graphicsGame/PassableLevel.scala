package graphicsGame
/**
 * a passable level that allows a level to be passed to a remote location
 */
case class PassableLevel(maze:Maze, entities:Seq[PassableEntity]) 