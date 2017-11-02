package graphicsGame

import scalafx.animation.AnimationTimer

object ServerMain {

  val maze = Maze.apply(5, false, 20, 20, .9)
  val level = new Level(maze, Nil)
  var dificulty = 6

  def randomInt(num: Int, num2: Int): (Int, Int) = {
    if (level.maze.isClear(num, num2, 1.25, 1.25)) {
      (num, num2)
    } else randomInt(scala.util.Random.nextInt(30) + 1, scala.util.Random.nextInt(30) + 1)
  }

  var radnums = for (i <- 0 until dificulty) yield {
    randomInt(scala.util.Random.nextInt(30) + 1, scala.util.Random.nextInt(30) + 1)
  }
  val player = new Player(radnums(0)_1, radnums(0)_2, level, false, false, false, false, false, false, false, false, 0,true)
  level.+=(player)

  for (i <- 0 until dificulty - 1) {
    if (i != 0) {
      val enemy = new Enemy(radnums(i)_1, radnums(i)_2, level, false, 0)
      level.+=(enemy)
    }
  }

  val bomber = new Bomber(radnums(5)_1, radnums(5)_2, level, false, 0)
  level.+=(bomber)

  var lastTime = 0L
  
  var clients:Seq[ClientMain]=Nil

  while(player.isalive){
    ClientMain.renderer.render(level.buildPassable, player.cx, player.cy)
    //ClientMain.fire()
    ClientMain.playerCol()
    ClientMain.enemyCol()
    // Code for doing smooth motion
    if (lastTime > 0) {
      val dt = (time - lastTime) * 1e-8
      level.updateAll(dt)

    }
    lastTime = time

  })
}