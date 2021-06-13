package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.tokens.Result

sealed trait Expression
case class ExpressionValue(res: Result)         extends Expression
case class ExpressionEval(op: Result => Result) extends Expression
