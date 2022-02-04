object AppendableThingsV1 {
    import scalaz._
import Scalaz._
import java.util.Currency

sealed abstract class Currency {
    val value: Double
}
case class USD(value: Double) extends Currency
case class EUR(value: Double) extends Currency

final case class TradeTemplate(
    payments: List[java.time.LocalDate],
    ccy: Option[Currency],
    otc: Option[Boolean]
)

object TradeTemplate {
    implicit val currencyMonoid: Monoid[Currency] = Monoid.instance((a,b) => USD(a.value + b.value), USD(0))
    implicit val otcMonoid: Monoid[Option[Boolean]] = Monoid.instance((a,b) => {
        for {
            aa <- a
            bb <- b
        } yield { aa && bb } 
    }, Some(true))
    implicit val monoid: Monoid[TradeTemplate] = Monoid.instance((a,b) => 
        TradeTemplate(a.payments |+| b.payments, a.ccy |+| b.ccy, a.otc |+| b.otc),
        TradeTemplate(Nil, None, None)
    )
}
}