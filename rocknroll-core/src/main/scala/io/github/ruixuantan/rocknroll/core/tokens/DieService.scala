package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.generators.{
  DefaultGenerator,
  Generator,
}
import io.github.ruixuantan.rocknroll.core.results.Result
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die
import io.github.ruixuantan.rocknroll.core.utils.MathUtil

class DieService(generator: Generator) extends ValueAlgebra[Die] {
  private def getVariance(die: Die): Double = {
    val variance = (((die.sides * die.sides) - 1) / 12.toDouble) * die.freq
    MathUtil.round(variance, 4)
  }

  private def getExpected(die: Die): Double =
    ((die.sides + 1) / 2.toDouble) * die.freq

  private def roll(die: Die): Int =
    generator.nextInt(die.sides)

  def getResult(die: Die): Result = {
    val res = (1 to die.freq).map(_ => roll(die)).sum
    Result(res, getExpected(die), getVariance(die))
  }
}

object DieService {
  def apply(generator: Generator = DefaultGenerator) =
    new DieService(generator)
}
