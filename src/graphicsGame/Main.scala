package graphicsGame

import scalafx.Includes.eventClosureWrapperWithParam
import scalafx.Includes.jfxKeyEvent2sfx
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyEvent


object Main extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "Graphics Game"
    scene = new Scene(800, 800) {
      val canvas = new Canvas(800, 800)
      val gc = canvas.graphicsContext2D

      content = canvas

      /**
       * this makes random numbers and checks to see if it is on a wall
       */
      var dificulty = 6
      val maze = Maze.apply(5, false, 20, 20, .9)
      val level = new Level(maze, Nil)

      def randomInt(num: Int, num2: Int): (Int, Int) = {
        if (level.maze.isClear(num, num2, 1.25, 1.25)) {
          (num, num2)
        } else randomInt(scala.util.Random.nextInt(30) + 1, scala.util.Random.nextInt(30) + 1)
      }

      var radnums = for (i <- 0 until dificulty) yield {
        randomInt(scala.util.Random.nextInt(30) + 1, scala.util.Random.nextInt(30) + 1)
      }
      val player = new Player(radnums(0)_1, radnums(0)_2, level, false, false, false, false, false, false, false, false, 0)
      level.+=(player)
      var alive = true
      
      for (i <- 0 until dificulty-1) {
        if (i != 0) {
          val enemy = new Enemy(radnums(i)_1, radnums(i)_2, level, false, 0)
          level.+=(enemy)
        }
      }

      
      val bomber = new Bomber(radnums(5)_1,radnums(5)_2, level,false,0)
      level.+=(bomber)

      val renderer = new Renderer2D(gc, 25)

      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Up => player.moveUpPressed
          case KeyCode.Down => player.moveDownPressed
          case KeyCode.Left => player.moveLeftPressed
          case KeyCode.Right => player.moveRightPressed
          case KeyCode.Space => player.firePressed
          case _ =>
        }
      }
      onKeyReleased = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Up => player.moveUpReleased
          case KeyCode.Down => player.moveDownReleased
          case KeyCode.Left => player.moveLeftReleased
          case KeyCode.Right => player.moveRightReleased
          case KeyCode.Space => player.fireReleased
          case _ =>
        }
      }

      def fire(): Unit = {
        if (lastTime % 10 == 0) {
          if (player.isFireingUp) level.+=(new Projectile(player.cx, player.cy, level, true, false, false, false))
          if (player.isFireingDown) level.+=(new Projectile(player.cx, player.cy, level, false, true, false, false))
          if (player.isFireingLeft) level.+=(new Projectile(player.cx, player.cy, level, false, false, true, false))
          if (player.isFireingRight) level.+=(new Projectile(player.cx, player.cy, level, false, false, false, true))
        }
      }

      def playerCol(): Unit = {
        for (i <- 1 until level.entities.length) {
          if (level.entities(0).intersect(level.entities(i))) {
            alive = false
          }
        }
      }

      def enemyCol(): Unit = {
        for (i <- 0 until level.entities.length) {
          for (j <- 0 until level.entities.length) {
            if (level.entities(i).isEnemy && level.entities(j).isProjectile) {
              if (level.entities(i).intersect(level.entities(j))) {
                level.-=(level.entities(i))
              }
            }
          }
        }
      }
      

      // Used for smooth motion
      var lastTime = 0L

      val timer = AnimationTimer(time => {
        renderer.render(level.buildPassable , player.cx, player.cy)
        fire()
        playerCol()
        enemyCol()
        // Code for doing smooth motion
        if (lastTime > 0) {
          val dt = (time - lastTime) * 1e-8
          level.updateAll(dt)

        }
        lastTime = time

      })
      timer.start()
    }
  }
}