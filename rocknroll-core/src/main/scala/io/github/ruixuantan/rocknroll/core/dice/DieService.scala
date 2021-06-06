package io.github.ruixuantan.rocknroll.core.dice

import scala.util.Random

class DieService {
  //TODO: abstract out rng into a pure function
  private val rng = new Random()

  private def rollOnce(die: Die): Int =
    rng.nextInt(die.sides) + 1

  def roll(die: Die): Int =
    (1 to die.freq).map(_ => rollOnce(die)).sum
}

object DieService {
  def apply() =
    new DieService()
}
