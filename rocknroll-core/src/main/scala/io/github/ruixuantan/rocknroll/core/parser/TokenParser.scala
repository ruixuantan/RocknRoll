package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.tokens.Operator.{Add, Subtract}
import io.github.ruixuantan.rocknroll.core.tokens.Token
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}

object TokenParser {
  val dieSyntax      = """([1-9]\d{0,1})*d([1-9]\d{0,2})""".r
  val numberSyntax   = """^[1-9]\d*""".r
  val addSyntax      = """\+""".r
  val subtractSyntax = """-""".r

  private def convertToDie(input: String): Token = {
    val arr  = input.split('d')
    val freq = if (arr(0) == "") 1 else arr(0).toInt
    Die(arr(1).toInt, freq)
  }

  private def convertToNumber(input: String): Token =
    Number(input.toInt)

  def tokenize(input: String): Either[ParseError, Token] =
    input match {
      case dieSyntax(_*)      => Right(convertToDie(input))
      case numberSyntax(_*)   => Right(convertToNumber(input))
      case addSyntax(_*)      => Right(Add)
      case subtractSyntax(_*) => Right(Subtract)
      case _                  => Left(ParseTokenError)
    }
}
