package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.generators.{
  DefaultGenerator,
  Generator,
}
import io.github.ruixuantan.rocknroll.core.results.Result
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die
import io.github.ruixuantan.rocknroll.core.utils.MathUtil

class DieAlgebraInterpreter(generator: Generator) extends ValueAlgebra[Die] {
  private def getVariance(die: Die): Double = {
    val variance = (((die.sides * die.sides) - 1) / 12.toDouble) * die.frequency
    MathUtil.round(variance, 4)
  }

  private def getExpected(die: Die): Double =
    ((die.sides + 1) / 2.toDouble) * die.frequency

  private def roll(die: Die): Int =
    generator.nextInt(die.sides)

  def getResult(die: Die): Result = {
    val res = (1 to die.frequency).map(_ => roll(die)).sum
    Result(
      res,
      getExpected(die),
      getVariance(die),
      die.frequency,
      die.sides * die.frequency,
      die.frequency,
    )
  }
}

object DieAlgebraInterpreter {
  def apply(generator: Generator = DefaultGenerator): DieAlgebraInterpreter =
    new DieAlgebraInterpreter(generator)
}
