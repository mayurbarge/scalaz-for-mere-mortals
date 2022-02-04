package typeclasses.mappable

import scalaz._
import Scalaz._

// Bag is a container
final case class Bag[A](contents: A)
final case class Sugar(weight: Double)
final case class Stone(weight: Double)
final case class Water(weight: Double)
final case class SweetWater(weight: Double)

object Bag {
    implicit def bagFunctor = new Functor[Bag] {
        def map[A, B](a: Bag[A])(f: A => B): Bag[B] = Bag(f(a.contents))
    }
}
object FunctorExample extends App {
    val functor: Functor[Bag] = Functor.apply[Bag]
    val bagOfWater: Bag[Water] = functor.map(Bag(Sugar(10)))( sugar => Water(sugar.weight))
    val sugarToWater: Bag[Sugar] => Bag[Water] = functor.lift((sugar: Sugar) => Water(sugar.weight))

    println("Bag of water: " + bagOfWater)
    println("Bag of Sugar to Water: " + sugarToWater(Bag(Sugar(2))))
    println("Bag of Sugar and bag of Water: " + functor.fproduct(Bag(Sugar(2)))(sugar => Water(0)) )
    println("Sugar added to bag of Water: " + functor.strengthL(Sugar(3.3), Bag(Water(3))))
    println("Water added to bag of Sugar: " + functor.strengthR(Bag(Sugar(3.3)), Water(3)))

    val mixer: Sugar => SweetWater = (sugar: Sugar) => SweetWater(sugar.weight+10)
    // second parameter is F[A => B] i.e. container with a mapping function.
    val sweetWater: Bag[SweetWater] = functor.mapply(Sugar(10))(Bag(mixer))
    println("Adding sugar to a bag of mixer to make SweetWater: " + sweetWater)
    println("Bag of Sugar is lifted with mixer to producer SweetWater: " + functor.lift(mixer)(Bag(Sugar(4))))
    println("Anything in a bag converted to Stone: " + Bag(Sugar(2)).as(Stone(2)))
    println("Anything in a bag converted to Stone: " + Bag(Water(2)) .>| (Stone(2)) )
}