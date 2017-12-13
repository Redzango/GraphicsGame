package graphicsGame

import scalafx.animation.AnimationTimer
import java.rmi.server.UnicastRemoteObject
import java.rmi.Naming
import java.rmi.registry.LocateRegistry
/**
 * alows a remote server to acess the methods and info
 */
@remote trait RemoteServer {
  def connect(client: RemoteClient): RemotePlayer
}
/**
 * the server object that creates the maze and level, runs the game, and connects to remote clients
 */
object ServerMain extends UnicastRemoteObject with App with RemoteServer {
  LocateRegistry.createRegistry(1099)
  Naming.rebind("GraphicsGame", this)

  val maze = Maze.apply(5, false, 20, 20, .9)
  val level = new Level(maze, Nil)
  val dificulty = 9

  def randomInt(num: Int, num2: Int): (Int, Int) = {
    if (level.maze.isClear(num, num2, 1.25, 1.25)) {
      (num, num2)
    } else randomInt(scala.util.Random.nextInt(100) + 1, scala.util.Random.nextInt(100) + 1)
  }

  var radnums = for (i <- 0 until dificulty) yield {
    randomInt(scala.util.Random.nextInt(100) + 1, scala.util.Random.nextInt(100) + 1)
  }

  for (i <- 1 until dificulty - 3) {
    if (i != 0) {
      val generator = new Generator(radnums(i)_1, radnums(i)_2, level, true)
      level.+=(generator)
    }
  }

  val bomber = new Bomber(radnums(dificulty - 1)_1, radnums(dificulty - 1)_2, level, true, 0)
  level.+=(bomber)
  val bomber1 = new Bomber(radnums(dificulty - 2)_1, radnums(dificulty - 2)_2, level, true, 0)
  level.+=(bomber1)
  val bomber2 = new Bomber(radnums(dificulty - 3)_1, radnums(dificulty - 3)_2, level, true, 0)
  level.+=(bomber2)

  var clients: List[RemoteClient] = Nil

  def connect(client: RemoteClient): RemotePlayer = {
    val player = new Player(radnums(0)_1, radnums(0)_2, level, false, false, false, false, false, 0, true)
    clients ::= client
    level += player
    println("client connected")
    player
  }

  var lastTick = 0L
  var tickint = 0.1

  while (true) {
    val time = System.nanoTime()
    val dt = (time - lastTick) * 1e-8
    if (dt > tickint) {
      level.updateAll(dt)
      val pl = level.buildPassable
      for (p <- clients) {
        p.update(pl)
        //p.draw
      }
      lastTick = time
    }
  }
}