package io.github.ruixuantan.rocknroll.core.dice

import io.github.ruixuantan.rocknroll.core.generators.{
  DefaultGenerator,
  Generator,
}

class DieService(generator: Generator) {
  private def getExpected(die: Die): Double =
    ((die.sides + 1) / 2.toDouble) * die.freq

  // TODO: Implement FFT
  private def getProbability(res: Int, die: Die): Double = 1.0

  private def rollOnce(die: Die): Int =
    generator.nextInt(die.sides)

  def roll(die: Die): DieResult = {
    val res = (1 to die.freq).map(_ => rollOnce(die)).sum
    DieResult(res, getExpected(die), getProbability(res, die))
  }
}

object DieService {
  def apply(generator: Generator = DefaultGenerator) =
    new DieService(generator)
}
