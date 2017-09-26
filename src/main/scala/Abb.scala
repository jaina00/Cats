import cats._
import cats.data._
import cats.implicits._

object Abb extends App {

  val f = (_: Int) * 2

  val g = (_: Int) + 10

  val h = (g andThen f) (8)

  println(">>>>>>>>>>>>>" + h)

  val i = (g map f) (8)

  println(">>>>>>>>>>>>>" + i)

  val c = (f |@| g) map {_ + _}
  println(">>>>>>>>>>>>>" + i)
}