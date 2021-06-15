package io.github.ruixuantan.rocknroll.core.parser

import io.github.ruixuantan.rocknroll.core.results.Result

sealed trait Expression
case class ExpressionValue(res: Result)         extends Expression
case class ExpressionEval(op: Result => Result) extends Expression
