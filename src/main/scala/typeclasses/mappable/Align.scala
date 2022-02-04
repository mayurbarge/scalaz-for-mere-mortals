package typeclasses.mappable

import scalaz._
import Scalaz._

final case class Apple(weight: Double)
final case class Orange(weight: Double)
object Apple {
    implicit def appleSemigroup = new Semigroup[Apple] {
        def append(a1: Apple, a2: => Apple): Apple = Apple(a1.weight+a2.weight)
    }
}
final case class Box[A](items: List[A])
object Box {
    implicit def distanceAlign = new Align[Box] {
      override def map[A, B](fa: Box[A])(f: A => B): Box[B] = Box(fa.items.map(f))

      override def alignWith[A, B, C](f: A \&/ B => C): (Box[A], Box[B]) => Box[C] = { (boxA: Box[A], boxB: Box[B]) => {
          val items = for {
              a <- boxA.items
              b <- boxB.items
          } yield \&/(a,b)
          Box(items.map(f))
        }
      }
    }
}
object AlignExample extends App {
    Map("foo" -> List(1)) merge Map("foo" -> List(1), "bar" -> List(2))

    val mergedApples = Box[Apple](List(Apple(1))) merge Box[Apple](List(Apple(2)))
    val alignedApples = Box[Apple](List(Apple(1))) alignA Box[Orange](List(Orange(2)))
    val alignedOranges = Box[Apple](List(Apple(1))) alignB Box[Orange](List(Orange(2)))
    val alignedAppleAndOranges = Box[Apple](List(Apple(1))) alignBoth Box[Orange](List(Orange(2)))

    println("Merge boxes with apples: " + mergedApples)
    println("AlignA : " + alignedApples)
    println("AlignB : " + alignedOranges)
    println("AlignBoth : " + alignedAppleAndOranges)

}