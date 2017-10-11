import cats.data.StateT
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


//The State monad is a generalization of the reader monad in the sense that each step can modify the current
//state before the next step is called. As a referentially transparent language cannot have
//a shared global state, the trick is to encapsulate the state inside the monad and pass
//it explicitly to each part of the sequence.

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
    Future(End, s"The state is $state")
  }

  val r: StateT[Future, ComputationState, (String)] = for {
    x <- b
    y <- c
    z <- d
  } yield z

  val g: Future[(ComputationState, String)] = r.run(Ready)
    .recover {
      case e => throw e
    }

  val t = Await.result(g, Duration.Inf)
  println(t)
  //(End$@4e1d422d,The state is Ran$@6b96c50a)
}