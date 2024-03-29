package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.tokens.Operator.{Add, Separate, Subtract}
import io.github.ruixuantan.rocknroll.core.tokens.Token
import io.github.ruixuantan.rocknroll.core.tokens.Value.{Die, Number}
import io.github.ruixuantan.rocknroll.core.tokens.TokenInstances.tokenAlgebraInterpreter.prettyPrint

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

  def prettyPrintTokens(input: List[Token]): String =
    input.map(prettyPrint).mkString(" ")

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
    def handleIsTokenOperator(
        inputToken: String,
        tokenOp: Token,
        lsBuffer: ListBuffer[Token],
        input: String,
    ): Either[ParseError, (ListBuffer[Token], String)] =
      for {
        tokenVal <- tokenizeValue(inputToken)
      } yield (lsBuffer.addOne(tokenVal).addOne(tokenOp), input.tail)

    @tailrec
    def parseTokenInner(
        inputToken: String,
        input: String,
    ): Either[ParseError, (ListBuffer[Token], String)] = {
      val lsBuffer = new ListBuffer[Token]()
      if (input.isEmpty)
        for {
          token <- tokenizeValue(inputToken)
        } yield (lsBuffer.addOne(token), input)
      else {
        val nextChar = input.head.toString
        tokenizeOperator(nextChar) match {
          case Right(tokenOp) => handleIsTokenOperator(inputToken, tokenOp, lsBuffer, input)
          case Left(_)        => parseTokenInner(inputToken + nextChar, input.tail)
        }
      }
    }

    def parseInner(
        input: String,
        lsBuffer: ListBuffer[Token],
    ): Either[ParseError, ListBuffer[Token]] =
      for {
        pair <- parseTokenInner("", input)
        parsedBuffer <-
          if (pair._2.isEmpty)
            Right(lsBuffer ++ pair._1)
          else
            parseInner(pair._2, lsBuffer ++ pair._1)
      } yield parsedBuffer

    for {
      ls <- parseInner(input, new ListBuffer[Token]())
    } yield ls.toList
  }
}

object TokenParser {
  def apply(): TokenParser = new TokenParser()
}
