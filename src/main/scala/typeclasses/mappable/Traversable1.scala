package typeclasses.mappable
/*


import simulacrum.{TypeClassMacros, typeclass}

// simulacrum annotation
@typeclass
trait Monoid[T] {
  def empty: T
  def combine(t1: T, t2: T): T
}

@typeclass
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}
@typeclass
trait Monoidal[F[_]] extends Functor[F]{
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
  def pure[A](a: A): F[A]
}

object Traversable1 extends App {
  implicit def monoidalOption = new Monoidal[Option] {
    override def product[A, B](fa: Option[A], fb: Option[B]): Option[(A, B)] = {
      for {
        a <- fa
        b <- fb
      } yield (a, b)
    }

    override def pure[A](a: A): Option[A] = Some(a)

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
  }

  //def sequence[F[_]: Monoidal, A](l: List[F[A]]): F[List[A]] = {
  def sequence[F[_]: Monoidal, A](l: List[F[A]]): F[List[A]] = {
  l.foldRight(Monoidal[F].pure(List.empty[A])) {
      (fa: F[A], acc: F[List[A]]) =>
        val prod: F[(A, List[A])] = fa.product(acc)
        prod.map(_ +: _)
    }
  }

  def traverse[F[_]: Monoidal, A, B](l: List[A])(f: A => F[B]): F[List[B]] = {
    l.foldRight(Monoidal[F].pure(List.empty[B])) {
      (a: A, acc: F[List[B]]) =>
        val product: F[(B, List[B])] = f(a).product(acc)
        product.map(_ +: _)
    }
  }

  println(sequence(List[Option[Int]](Some(1),Some(2))))
}
*/
