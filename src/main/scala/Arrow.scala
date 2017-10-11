import cats.syntax.cartesian._
import cats.instances.future._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Arrow {

//  def +++[A, B, C](a: A => Future[B], b: A => Future[C]): A => Future[(B, C)] = (in: A) => {
//    (a(in) |@| b(in)).tupled
//  }
//
//  def +++[A, B, C](a: A => B, b: A => C): A => (B, C) = (in: A) => {
//    (a(in), b(in))
//  }

}
