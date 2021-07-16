package io.github.ruixuantan.rocknroll.core.parser

import cats.implicits._
import io.github.ruixuantan.rocknroll.core.generators.Generator
import io.github.ruixuantan.rocknroll.core.results.Result
import io.github.ruixuantan.rocknroll.core.tokens.{Operator, Token, Value}
import io.github.ruixuantan.rocknroll.core.tokens.Operator.{Add, Separate, Subtract}
import io.github.ruixuantan.rocknroll.core.tokens.ValueInstances
import io.github.ruixuantan.rocknroll.core.tokens.ValueSyntax._
import io.github.ruixuantan.rocknroll.core.results.ResultInstances._
import io.github.ruixuantan.rocknroll.core.results.ResultInstances.resultAlgebraInterpreter.identity
import io.github.ruixuantan.rocknroll.core.results.ResultSyntax._
import io.github.ruixuantan.rocknroll.core.utils.MathUtil

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.math.sqrt

class DieParserService(tokenParser: TokenParser) extends DieParserAlgebra {
  override def parse(input: String): Either[ParseError, List[Token]] =
    tokenParser.parse(input.replaceAll("\\s", ""))

  override def validate(tokens: List[Token]): Boolean = {
    @tailrec
    def validateInner(tokens: List[Token], prev: Token): Boolean =
      if (tokens.length == 1) {
        tokens.head.isInstanceOf[Value]
      } else {
        prev match {
          case _: Value    => tokens.head.isInstanceOf[Operator] && validateInner(tokens.tail, tokens.head)
          case _: Operator => tokens.head.isInstanceOf[Value] && validateInner(tokens.tail, tokens.head)
        }
      }
    validateInner(tokens, Operator.Add)
  }

  private def getFinalResult(tokens: List[Token], result: Result): FinalResult =
    FinalResult(
      tokenParser.prettyPrintTokens(tokens),
      result.result,
      result.expected,
      MathUtil.round(sqrt(result.variance), 3),
      result.lowerBound,
      result.upperBound,
      result.diceRolled,
    )

  def handleEvalOperator(
      tokens: List[Token],
      op: Operator,
      acc: Result,
      valueInstances: ValueInstances,
  ): Either[ParseOrderError.type, Result] = {
    import valueInstances.valueAlgebraInterpreter

    tokens.headOption
      .map {
        case value: Value =>
          op match {
            case Add      => evalTokens(tokens.tail, acc + value.getResult, valueInstances)
            case Subtract => evalTokens(tokens.tail, acc - value.getResult, valueInstances)
            case _        => Left(ParseOrderError)
          }
        case _ => Left(ParseOrderError)
      }
      .getOrElse(Left(ParseOrderError))
  }

  def evalTokens(
      tokens: List[Token],
      acc: Result,
      valueInstances: ValueInstances,
  ): Either[ParseOrderError.type, Result] =
    tokens.headOption
      .map {
        case op: Operator => handleEvalOperator(tokens.tail, op, acc, valueInstances)
        case _            => Left(ParseOrderError)
      }
      .getOrElse(Right(acc))

  @tailrec
  private def separateTokens(
      tokens: List[Token],
      tokenBuffer: ListBuffer[Token],
      lsBuffer: ListBuffer[List[Token]],
  ): List[List[Token]] =
    if (tokens.isEmpty)
      lsBuffer.addOne(tokenBuffer.toList).toList
    else if (tokens.head == Separate)
      separateTokens(tokens.tail, new ListBuffer[Token](), lsBuffer.addOne(tokenBuffer.toList))
    else
      separateTokens(tokens.tail, tokenBuffer.addOne(tokens.head), lsBuffer)

  override def eval(
      tokens: List[Token],
      generator: Generator,
  ): Either[ParseError, List[FinalResult]] = {
    val separated      = separateTokens(tokens, new ListBuffer[Token](), new ListBuffer[List[Token]]())
    val valueInstances = ValueInstances(generator)
    for {
      results <- separated.map(tokenList => evalTokens(Add :: tokenList, identity, valueInstances)).sequence
    } yield separated.zip(results).map(elem => getFinalResult(elem._1, elem._2))
  }
}

object DieParserService {
  def apply(tokenParser: TokenParser) =
    new DieParserService(tokenParser)
}
