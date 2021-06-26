package io.github.ruixuantan.rocknroll.core.parser

import cats.implicits._
import io.github.ruixuantan.rocknroll.core.results.{Result, ResultAlgebra}
import io.github.ruixuantan.rocknroll.core.tokens.{Operator, Token, Value}
import io.github.ruixuantan.rocknroll.core.tokens.Operator.{
  Add,
  Separate,
  Subtract,
}
import io.github.ruixuantan.rocknroll.core.utils.MathUtil

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.math.sqrt

class DieParserService(tokenParser: TokenParser, resultAlgebra: ResultAlgebra)
    extends DieParserAlgebra {
  override def parse(input: String): Either[ParseError, List[Token]] =
    tokenParser.parse(input.replaceAll("\\s", ""))

  override def validate(tokens: List[Token]): Boolean = {
    @tailrec
    def validateInner(tokens: List[Token], prev: Token): Boolean =
      if (tokens.length == 1) {
        tokens.head.isInstanceOf[Value]
      } else {
        prev match {
          case _: Value =>
            tokens.head
              .isInstanceOf[Operator] && validateInner(tokens.tail, tokens.head)
          case _: Operator =>
            tokens.head
              .isInstanceOf[Value] && validateInner(tokens.tail, tokens.head)
        }
      }

    validateInner(tokens, Operator.Add)
  }

  private def getExpressionValue(
      value: Value,
      exprEval: ExpressionEval,
  ): ExpressionValue =
    ExpressionValue(exprEval.op(resultAlgebra.getResult(value)))

  private def getExpressionEval(
      op: Operator,
      exprValue: ExpressionValue,
  ): ExpressionEval =
    op match {
      case Add      => ExpressionEval(resultAlgebra.add(exprValue.res))
      case Subtract => ExpressionEval(resultAlgebra.subtract(exprValue.res))
    }

  private def evalExpression(
      expr: Expression,
      handleVal: ExpressionValue => Either[ParseOrderError.type, FinalResult],
      handleEval: ExpressionEval => Either[ParseOrderError.type, FinalResult],
  ): Either[ParseOrderError.type, FinalResult] =
    expr match {
      case exprValue: ExpressionValue => handleVal(exprValue)
      case exprEval: ExpressionEval   => handleEval(exprEval)
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

  private def evalHelper(
      originalTokens: List[Token],
      tokens: List[Token],
      acc: Expression,
  ): Either[ParseOrderError.type, FinalResult] =
    if (tokens.isEmpty) {
      evalExpression(
        acc,
        exprValue => Right(getFinalResult(originalTokens, exprValue.res)),
        _ => Left(ParseOrderError),
      )
    } else {
      tokens.head match {
        case value: Value =>
          evalExpression(
            acc,
            _ => Left(ParseOrderError),
            exprEval =>
              evalHelper(
                originalTokens,
                tokens.tail,
                getExpressionValue(value, exprEval),
              ),
          )
        case op: Operator =>
          evalExpression(
            acc,
            exprValue =>
              evalHelper(
                originalTokens,
                tokens.tail,
                getExpressionEval(op, exprValue),
              ),
            _ => Left(ParseOrderError),
          )
      }
    }

  override def eval(
      tokens: List[Token],
  ): Either[ParseError, List[FinalResult]] = {
    @tailrec
    def evalSeparate(
        tokens: List[Token],
        tokenBuffer: ListBuffer[Token],
        lsBuffer: ListBuffer[List[Token]],
    ): List[List[Token]] =
      if (tokens.isEmpty)
        lsBuffer.addOne(tokenBuffer.toList).toList
      else if (tokens.head == Separate)
        evalSeparate(
          tokens.tail,
          new ListBuffer[Token](),
          lsBuffer.addOne(tokenBuffer.toList),
        )
      else
        evalSeparate(tokens.tail, tokenBuffer.addOne(tokens.head), lsBuffer)

    val initial = ExpressionEval(resultAlgebra.add(resultAlgebra.identity))
    evalSeparate(tokens, new ListBuffer[Token](), new ListBuffer[List[Token]]())
      .map(tokenList => evalHelper(tokenList, tokenList, initial))
      .sequence
  }
}

object DieParserService {
  def apply(tokenParser: TokenParser, resultService: ResultAlgebra) =
    new DieParserService(tokenParser, resultService)
}
