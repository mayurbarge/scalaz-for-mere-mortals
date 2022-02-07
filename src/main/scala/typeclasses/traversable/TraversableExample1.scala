package typeclasses.traversable

import scalaz.{Applicative, EitherT, EitherTFoldable, Traverse}
import scalaz.Scalaz.optionInstance

trait Traverse[T[_]] {
  //def sequence[F[_]: Applicative, A](tfa: T[F[A]]): F[T[A]]
  def traverse[F[_]: Applicative, A, B](ta: T[A])(f: A => F[B]): F[T[B]]
}
object T {
  implicit val traverseForList: Traverse[List] = new Traverse[List] {
    override def traverse[F[_] : Applicative, A, B](ta: List[A])(f: A => F[B]): F[List[B]] = {
      ta.foldRight(Applicative[F].pure(List.empty[B])) { (a, acc) =>
        Applicative[F].apply2(f(a), acc)(_ :: _)
      }
    }
  }
}
object TraversableExample1 extends App {
  import T._
  val list = List(Some(1), Some(2), None)
  val traversed = T.traverseForList.traverse(list)(identity)

  println(traversed)
}