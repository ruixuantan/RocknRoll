package io.github.ruixuantan.rocknroll.core.parser

import cats.implicits._
import io.github.ruixuantan.rocknroll.core.results.{Result, ResultAlgebra}
import io.github.ruixuantan.rocknroll.core.tokens.{Operator, Token, Value}
import io.github.ruixuantan.rocknroll.core.tokens.Operator.{
  Add,
  Separate,
  Subtract,
}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

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
      handleVal: ExpressionValue => Either[ParseOrderError.type, Result],
      handleEval: ExpressionEval => Either[ParseOrderError.type, Result],
  ): Either[ParseOrderError.type, Result] =
    expr match {
      case exprValue: ExpressionValue => handleVal(exprValue)
      case exprEval: ExpressionEval   => handleEval(exprEval)
    }

  private def evalHelper(
      tokens: List[Token],
      acc: Expression,
  ): Either[ParseOrderError.type, Result] =
    if (tokens.isEmpty) {
      evalExpression(
        acc,
        exprValue => Right(exprValue.res),
        _ => Left(ParseOrderError),
      )
    } else {
      tokens.head match {
        case value: Value =>
          evalExpression(
            acc,
            _ => Left(ParseOrderError),
            exprEval =>
              evalHelper(tokens.tail, getExpressionValue(value, exprEval)),
          )
        case op: Operator =>
          evalExpression(
            acc,
            exprValue =>
              evalHelper(tokens.tail, getExpressionEval(op, exprValue)),
            _ => Left(ParseOrderError),
          )
      }
    }

  override def eval(tokens: List[Token]): Either[ParseError, List[Result]] = {
    @tailrec
    def evalInner(
        tokens: List[Token],
        tokenBuffer: ListBuffer[Token],
        lsBuffer: ListBuffer[List[Token]],
    ): List[List[Token]] =
      if (tokens.isEmpty)
        lsBuffer.addOne(tokenBuffer.toList).toList
      else if (tokens.head == Separate)
        evalInner(
          tokens.tail,
          new ListBuffer[Token](),
          lsBuffer.addOne(tokenBuffer.toList),
        )
      else
        evalInner(tokens.tail, tokenBuffer.addOne(tokens.head), lsBuffer)

    val initial = ExpressionEval(resultAlgebra.add(resultAlgebra.identity))
    evalInner(tokens, new ListBuffer[Token](), new ListBuffer[List[Token]]())
      .map(tokenList => evalHelper(tokenList, initial))
      .sequence
  }
}

object DieParserService {
  def apply(tokenParser: TokenParser, resultService: ResultAlgebra) =
    new DieParserService(tokenParser, resultService)
}
