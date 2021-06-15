package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.tokens.Operator.{
  Add,
  Separate,
  Subtract,
}
import io.github.ruixuantan.rocknroll.core.tokens.Token
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

class TokenParser {
  val dieSyntax: Regex       = """([1-9]\d{0,1})*d([1-9]\d{0,2})""".r
  val numberSyntax: Regex    = """^[1-9]\d*""".r
  val addSyntax: Regex       = """\+""".r
  val subtractSyntax: Regex  = """-""".r
  val separatorSyntax: Regex = """/""".r

  private def convertToDie(input: String): Token = {
    val arr  = input.split('d')
    val freq = if (arr(0) == "") 1 else arr(0).toInt
    Die(arr(1).toInt, freq)
  }

  private def convertToNumber(input: String): Token =
    Number(input.toInt)

  def prettyPrintToken(input: Token): String =
    input match {
      case die: Die =>
        if (die.freq == 1) s"d${die.sides}" else s"${die.freq}d${die.sides}"
      case number: Number => number.number.toString
      case Add            => "+"
      case Subtract       => "-"
      case Separate       => "/"
    }

  def tokenizeValue(token: String): Either[ParseError, Token] =
    token match {
      case dieSyntax(_*)    => Right(convertToDie(token))
      case numberSyntax(_*) => Right(convertToNumber(token))
      case _                => Left(ParseTokenError)
    }

  def tokenizeOperator(token: String): Either[ParseError, Token] =
    token match {
      case addSyntax(_*)       => Right(Add)
      case subtractSyntax(_*)  => Right(Subtract)
      case separatorSyntax(_*) => Right(Separate)
      case _                   => Left(ParseTokenError)
    }

  def parse(input: String): Either[ParseError, List[Token]] = {

    def handleEmptyInput(
        inputToken: String,
        lsBuffer: ListBuffer[Token],
        input: String,
    ): Either[ParseError, (ListBuffer[Token], String)] =
      tokenizeValue(inputToken) match {
        case Right(t) => Right(lsBuffer.addOne(t), input)
        case Left(_)  => Left(ParseTokenError)
      }

    def handleIsTokenOperator(
        inputToken: String,
        tokenOp: Token,
        lsBuffer: ListBuffer[Token],
        input: String,
    ): Either[ParseError, (ListBuffer[Token], String)] =
      tokenizeValue(inputToken) match {
        case Right(tokenValue) =>
          Right(
            lsBuffer.addOne(tokenValue).addOne(tokenOp),
            input.tail,
          )
        case Left(_) => Left(ParseTokenError)
      }

    @tailrec
    def parseTokenInner(
        inputToken: String,
        input: String,
    ): Either[ParseError, (ListBuffer[Token], String)] = {
      val lsBuffer = new ListBuffer[Token]()
      if (input.isEmpty)
        handleEmptyInput(inputToken, lsBuffer, input)
      else {
        val nextChar = input.head.toString
        tokenizeOperator(nextChar) match {
          case Right(tokenOp) =>
            handleIsTokenOperator(inputToken, tokenOp, lsBuffer, input)
          case Left(_) =>
            parseTokenInner(inputToken + nextChar, input.tail)
        }
      }
    }

    @tailrec
    def parseInner(
        input: String,
        lsBuffer: ListBuffer[Token],
    ): Either[ParseError, ListBuffer[Token]] =
      parseTokenInner("", input) match {
        case Left(_) => Left(ParseTokenError)
        case Right(pair) =>
          if (pair._2.isEmpty)
            Right(lsBuffer ++ pair._1)
          else
            parseInner(pair._2, lsBuffer ++ pair._1)
      }

    parseInner(input, new ListBuffer[Token]()) match {
      case Right(lsBuffer) => Right(lsBuffer.toList)
      case Left(_)         => Left(ParseTokenError)
    }
  }
}

object TokenParser {
  def apply(): TokenParser = new TokenParser()
}
