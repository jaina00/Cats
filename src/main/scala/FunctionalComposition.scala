object FunctionalComposition extends App {

  object ThresholdOffer extends (List[Int] => Int) {
    override def apply(v1: List[Int]): Int = {
      val sum = v1.sum
      if (sum > 9000)
        (sum * 90) / 100
      else
        sum
    }
  }

  object KeyChainOffer extends (List[Int] => Int) {
    override def apply(v1: List[Int]): Int = 15000
  }

  val lstOfOffers: Seq[(List[Int]) => Int] = List(ThresholdOffer, KeyChainOffer)

  object YNAPPromotions extends (List[Int] => Int) {
    override def apply(v1: List[Int]): Int = {
      lstOfOffers.map(f => f(v1)).sorted.min
    }
  }

  def total(items: List[Int]): Int = YNAPPromotions apply items

  println(total(List(4500, 5000)))
}