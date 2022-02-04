package typeclasses

import scalaz._
import Scalaz._
import java.util.Currency

sealed abstract class Currency
case object USD extends Currency
case object EUR extends Currency

final case class TradeTemplate(
    payments: List[java.time.LocalDate],
    ccy: Option[Currency],
    otc: Option[Boolean]
)

object TradeTemplate {
    // last rule wins
    implicit def lastWins[A]: Monoid[Option[A]] = Monoid.instance((a,b) => {
        (a, b) match {
            case (None, None) => None
            case (only, None) => only
            case (None, only) => only
            case (_, winner) => winner
        }
    }, None)
    implicit val monoid: Monoid[TradeTemplate] = Monoid.instance((a,b) => 
        TradeTemplate(a.payments |+| b.payments, a.ccy |+| b.ccy, a.otc |+| b.otc),
        TradeTemplate(Nil, None, None)
    )
}

object AppendableThings extends App {
    import java.time.{LocalDate => LD}
    val templates = List(
        TradeTemplate(Nil,      None,           None),
        TradeTemplate(Nil,      Some(EUR),      None),
        TradeTemplate(List(LD.of(2017,1,1)), Some(USD), None),
        TradeTemplate(List(LD.of(2021,1,26)), None, Some(true)),
        TradeTemplate(Nil,      None, Some(false)))
    
    val zero = Monoid[TradeTemplate].zero
    println(templates.fold(zero)(_ |+| _))
}