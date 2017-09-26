import cats.data.StateT
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

trait ComputationState

object Ready extends ComputationState

object Start extends ComputationState

object Ran extends ComputationState

object End extends ComputationState

object StateMoandExample extends App {

  val b = StateT[Future, ComputationState, String] { state =>
    Future(Start, s"The state is $state")
  }

  val c = StateT[Future, ComputationState, String] { state =>
    Future(Ran, s"The state is $state")
  }

  val d = StateT[Future, ComputationState, String] { state =>
    Future {
      (End, s"The state is $state")
    }
  }

  val r: StateT[Future, ComputationState, (String, String, String)] = for {
    x <- b
    y <- c
    z <- d
  } yield (x, y, z)

  val g: Future[Any] = r.run(Ready)
    .recover {
      case e => println(e)
    }

  val t = Await.ready(g, Duration.Inf)
  println(t)

  //Future(Success((End$@2ac273d3,(The state is Ready$@2eb6560b,The state is Start$@465e0a4c,The state is Ran$@50663384))))
}