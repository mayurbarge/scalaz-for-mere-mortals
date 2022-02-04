package typeclasses.mappable

import scalaz._
import Scalaz._

trait PackingItem
trait RestrictedItem extends PackingItem
final case object Cloths extends PackingItem
final case object Food extends PackingItem
final case object Weed extends PackingItem with RestrictedItem

final case class TravelBag(items: List[PackingItem])
final case class FilteredItems[A](items: List[A])
object Foldable2 extends App {

    // this loop terminates early due to Option behaviour.
    // the moment None is reached operation is short-circuited
    // replacing None with Some(false) processes all elements
    /*
    println(
    IList.fromList(List(Cloths, Weed, Food))
        .foldLeftM[Option, Boolean](false)( (restrictedItems, item) => {
            println(restrictedItems)
            println(item)
            restrictedItems match {
                case false => if(item.isInstanceOf[RestrictedItem]) Some(true) else None
                case true => Some(true)
            }
        })
    )
    */

     // Monadic Folds: we can fold over a structure with a function
    // which returns its value in a Monad,
    val sumEvens: (Int,Int) => Option[Int] = { (x, y) =>
        // if the right int is even, add it to the left
        // otherwise return None
        if((y % 2) == 0) Some(x+y) else None
    }

     val allEvens = List(2,4,6,8,10)
    println(allEvens.foldLeftM[Option,Int](0)(sumEvens))


    val filterSuspect: (Boolean, PackingItem) => Option[Boolean] = (isPrevioudItemResticted, currentItem) => {
        println("Cheking item:: PreviousSuspect: " + isPrevioudItemResticted + " current: " + currentItem)
        //println(currentItem.isInstanceOf[RestrictedItem])
        if(isPrevioudItemResticted || currentItem.isInstanceOf[RestrictedItem]) {
          //  println("sending None")
            None
        }
        else if(currentItem.isInstanceOf[RestrictedItem]) Some(true) else Some(false)
    }

    // Use case is even if single item is restricted, do not let anything pass
    println("Packing Items allowed to carry: " +
    IList.fromList(List(Cloths, Weed, Food))
        .foldLeftM[Option, Boolean](false)(filterSuspect)
    )
    
}