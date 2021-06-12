package io.github.ruixuantan.rocknroll.core.parser

import cats.implicits._
import io.github.ruixuantan.rocknroll.core.tokens.{
  Operator,
  Result,
  ResultAlgebra,
  ResultService,
  Token,
  Value,
}
import io.github.ruixuantan.rocknroll.core.tokens.Operator.{Add, Subtract}

class DieParserService(resultAlgebra: ResultAlgebra) extends DieParserAlgebra {
  val splitChar = ' '

  override def parse(input: String): Either[ParseError, List[Token]] =
    input.split(splitChar).map(str => TokenParser.tokenize(str)).toList.sequence

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

  private def evalHelper(
      tokens: List[Token],
      acc: Expression,
  ): Either[ParseOrderError.type, Result] =
    tokens.head match {
      case value: Value =>
        acc match {
          case exprEval: ExpressionEval =>
            evalHelper(tokens.tail, getExpressionValue(value, exprEval))
          case _ => Left(ParseOrderError)
        }
      case op: Operator =>
        acc match {
          case exprValue: ExpressionValue =>
            evalHelper(tokens.tail, getExpressionEval(op, exprValue))
          case _ => Left(ParseOrderError)
        }
      case _ =>
        acc match {
          case exprValue: ExpressionValue => Right(exprValue.res)
          case _                          => Left(ParseOrderError)
        }
    }

  override def eval(tokens: List[Token]): Either[ParseError, Result] = {
    val initial = ExpressionEval(resultAlgebra.add(resultAlgebra.identity))
    evalHelper(tokens, initial)
  }
}

object DieParserService {
  def apply(resultService: ResultService) =
    new DieParserService(resultService)
}
