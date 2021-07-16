package io.github.ruixuantan.rocknroll.core

import io.github.ruixuantan.rocknroll.core.generators.CyclicalGenerator
import io.github.ruixuantan.rocknroll.core.tokens.Operator
import io.github.ruixuantan.rocknroll.core.tokens.Value.Die
import org.scalatest.funsuite.AnyFunSuite

class GeneratorServiceTest extends AnyFunSuite {
  val generatorService = GeneratorService(CyclicalGenerator(0))

  test("Generator Service test seed continuation") {
    generatorService.eval(List(Die(4, 1), Operator.Separate, Die(4, 1))) match {
      case Right(res) => assert(res.map(_.result) == List(1, 2))
      case Left(_)    => assert(false)
    }
  }
}
