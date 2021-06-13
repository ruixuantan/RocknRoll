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
import io.github.ruixuantan.rocknroll.core.tokens.Operator.{
  Add,
  Separate,
  Subtract,
}

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

class DieParserService(tokenParser: TokenParser, resultAlgebra: ResultAlgebra)
    extends DieParserAlgebra {
  val splitChar = ' '

  override def parse(input: String): Either[ParseError, List[Token]] =
    tokenParser.parse(input.replaceAll("\\s", ""))

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

  @tailrec
  private def evalHelper(
      tokens: List[Token],
      acc: Expression,
  ): Either[ParseOrderError.type, Result] =
    if (tokens.isEmpty) {
      acc match {
        case exprValue: ExpressionValue => Right(exprValue.res)
        case _                          => Left(ParseOrderError)
      }
    } else {
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
      }
    }

  override def eval(tokens: List[Token]): Either[ParseError, List[Result]] = {
    val initial = ExpressionEval(resultAlgebra.add(resultAlgebra.identity))

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

    evalInner(tokens, new ListBuffer[Token](), new ListBuffer[List[Token]]())
      .map(tokenList => evalHelper(tokenList, initial))
      .sequence
  }
}

object DieParserService {
  def apply(tokenParser: TokenParser, resultService: ResultService) =
    new DieParserService(tokenParser, resultService)
}
