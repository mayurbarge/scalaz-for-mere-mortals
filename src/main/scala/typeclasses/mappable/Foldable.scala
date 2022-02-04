package typeclasses.mappable

import scalaz._
import Scalaz._
import typeclasses.TradeTemplate
import typeclasses.EUR
import typeclasses.USD
import typeclasses.TradeTemplate._

object FoldableExample extends App {
    sealed abstract class Color
    final case object Red extends Color
    final case object Green extends Color

    final case class Cat(name: String, beltColor: Color)
    object Cat {
        implicit def catEquals = new Equal[Cat] {
          override def equal(cat1: Cat, cat2: Cat): Boolean = cat1.name === cat2.name
        }
    }

    import java.time.{LocalDate => LD}
    val templates = List(
        TradeTemplate(Nil,      None,           None),
        TradeTemplate(Nil,      Some(EUR),      None),
        TradeTemplate(List(LD.of(2017,1,1)), Some(USD), None),
        TradeTemplate(List(LD.of(2021,1,26)), None, Some(true)),
        TradeTemplate(Nil,      None, Some(false)))
    
    println("Usgin Scalaz list for safe folding: " + templates.toIList.fold)
    println("Get -1 when item not found in list safely: " + List(1).indexOf(2))
    println("Get index of item in the list: " + List(1,4,2).indexOf(4))

    // similar to List.contains but uses Equal for comparison
    println("Compare my cat even if someone stole her and changed the color: " + 
                            List(Cat("Tom", Red)).element(Cat("Tom", Green)) )
}