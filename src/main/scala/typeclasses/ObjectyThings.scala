package typeclasses

import scalaz._
import Scalaz._
import java.time.{LocalDate => LD}

case class Bill(date: LD, amount: Double)
object Bill {
    implicit def orderBill: Order[Bill] = new Order[Bill] {
      override def order(x: Bill, y: Bill): Ordering = {
            x.amount ?|? y.amount
        }
    }
}

object ObjectyThings extends App {
    println(0 |-> 10)
    println(0 |--> (10,3))
    println('a' |-> 'z')
    println(0 -+- 1)
    println(1 --- 0)
    println(List(1,2,3) === List(1,2,3))
    println("Two bills are equal in amount ? "+ (Bill(LD.now(), 10) > Bill(LD.of(2022,1,1), 50)))   
}