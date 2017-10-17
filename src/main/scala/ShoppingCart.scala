sealed trait Item {
  val price: Int
}

case class Apple(price: Int = 60) extends Item

case class Banana(price: Int = 25) extends Item


object Promotions {

  val noOffer: (List[Item] => Int) = (basket: List[Item]) => basket.map(_.price).sum

  val simpleOffer: (List[Item] => Int) = (basket: List[Item]) => {
    val group = basket.groupBy(identity).mapValues(_.size)

    val applyOffer: Map[Item, Int] = group.map(x => {
      x._1 match {
        case a: Apple => (a, x._2 / 2 + x._2 % 2)
        case b: Banana => (b, (2 * x._2 / 3) + x._2 % 3)
        case z => (z, x._2)
      }
    })

    applyOffer.foldLeft(0) {
      (acc: Int, ele: (Item, Int)) =>
        acc + (ele._1.price * ele._2)
    }
  }

  val lstOfPromotions: Seq[(List[Item]) => Int] = List(noOffer, simpleOffer)

}

object ShoppingCart {

  import Promotions._

  def calculateTotal(items: List[Item]): Int = {
    lstOfPromotions.map(offer => offer(items)).min
  }

}
