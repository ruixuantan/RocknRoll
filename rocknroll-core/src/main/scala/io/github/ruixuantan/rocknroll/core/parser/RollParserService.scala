package io.github.ruixuantan.rocknroll.core.parser

import cats.implicits._
import io.github.ruixuantan.rocknroll.core.dice.Die

class RollParserService {
  private val splitChar = 'd'

  private def parseOneRoll(input: String): Either[ParseError, Die] = {
    val splitArr = input.split(splitChar)
    try {
      val freq = if (splitArr(0) == "") 1 else splitArr(0).toInt
      Right(Die(splitArr(1).toInt, freq))
    } catch {
      case _: NumberFormatException => Left(RollParserError)
    }
  }

  def parse(input: String): Either[ParseError, List[Die]] =
    input.split(' ').map(s => parseOneRoll(s)).toList.sequence
}

object RollParserService {
  def apply() =
    new RollParserService()
}
