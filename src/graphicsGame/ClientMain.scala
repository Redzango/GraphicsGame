package graphicsGame

import scalafx.Includes.eventClosureWrapperWithParam
import scalafx.Includes.jfxKeyEvent2sfx
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.KeyCode
import scalafx.scene.input.KeyEvent
import java.rmi.server.UnicastRemoteObject
import java.rmi.Naming
import scalafx.application.Platform
import scalafx.scene.paint.Color
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.text.Text

@remote trait RemoteClient {
  def update(level: PassableLevel): Unit
  def draw: Unit
}

object ClientMain extends UnicastRemoteObject with JFXApp with RemoteClient {

  val server = Naming.lookup("rmi://localhost/GraphicsGame") match {
    case s: RemoteServer => s
  }
  val canvas = new Canvas(800, 800)
  val gc = canvas.graphicsContext2D
  val renderer = new Renderer2D(gc, 25)
  val player = server.connect(this)

  def update(level: PassableLevel): Unit = {
    Platform.runLater(renderer.render(level, player.cx, player.cy))
  }

  def draw: Unit = {
    gc.fill = Color.Black
    gc.fillText(player.scor.toString(), 100, 100, 100)
  }

  stage = new JFXApp.PrimaryStage {
    title = "Graphics Game"
    scene = new Scene(800, 800) {
      content = canvas
      /**
       * this makes random numbers and checks to see if it is on a wall
       */

      onKeyPressed = (ke: KeyEvent) => {
        ke.code match {
          case KeyCode.Up => {
            player.moveUpPressed
            player.chLastMove(0)
          }
          case KeyCode.Down => {
            player.moveDownPressed
            player.chLastMove(1)
          }
          case KeyCode.Left => {
            player.moveLeftPressed
            player.chLastMove(2)
          }
          case KeyCode.Right => {
            player.moveRightPressed
            player.chLastMove(3)
          }
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
    }
  }

}