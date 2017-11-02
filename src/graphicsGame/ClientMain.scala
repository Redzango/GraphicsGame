package graphicsGame

import scalafx.Includes.eventClosureWrapperWithParam
import scalafx.Includes.jfxKeyEvent2sfx
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyEvent


object ClientMain extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title = "Graphics Game"
    scene = new Scene(800, 800) {
      val canvas = new Canvas(800, 800)
      val gc = canvas.graphicsContext2D

      content = canvas

      /**
       * this makes random numbers and checks to see if it is on a wall
       */
      val renderer = new Renderer2D(gc, 25)

      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Up => ServerMain.player.moveUpPressed
          case KeyCode.Down => ServerMain.player.moveDownPressed
          case KeyCode.Left => ServerMain.player.moveLeftPressed
          case KeyCode.Right => ServerMain.player.moveRightPressed
          case KeyCode.Space => ServerMain.player.firePressed
          case _ =>
        }
      }
      onKeyReleased = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Up => ServerMain.player.moveUpReleased
          case KeyCode.Down => ServerMain.player.moveDownReleased
          case KeyCode.Left => ServerMain.player.moveLeftReleased
          case KeyCode.Right => ServerMain.player.moveRightReleased
          case KeyCode.Space => ServerMain.player.fireReleased
          case _ =>
        }
      }

      /*def fire(): Unit = {
        if (lastTime % 10 == 0) {
          if (player.isFireingUp) level.+=(new Projectile(player.cx, player.cy, level, true, false, false, false))
          if (player.isFireingDown) level.+=(new Projectile(player.cx, player.cy, level, false, true, false, false))
          if (player.isFireingLeft) level.+=(new Projectile(player.cx, player.cy, level, false, false, true, false))
          if (player.isFireingRight) level.+=(new Projectile(player.cx, player.cy, level, false, false, false, true))
        }
      }*/

      def playerCol(): Unit = {
        for (i <- 1 until ServerMain.level.entities.length) {
          if (ServerMain.level.entities(0).intersect(ServerMain.level.entities(i))) {
            ServerMain.player.isDie
          }
        }
      }

      def enemyCol(): Unit = {
        for (i <- 0 until ServerMain.level.entities.length) {
          for (j <- 0 until ServerMain.level.entities.length) {
            if (ServerMain.level.entities(i).isEnemy && ServerMain.level.entities(j).isProjectile) {
              if (ServerMain.level.entities(i).intersect(ServerMain.level.entities(j))) {
                ServerMain.level.-=(ServerMain.level.entities(i))
              }
            }
          }
        }
      }
    }
  }
}