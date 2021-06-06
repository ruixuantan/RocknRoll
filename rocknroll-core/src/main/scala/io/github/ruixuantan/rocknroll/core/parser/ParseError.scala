package io.github.ruixuantan.rocknroll.core.parser

sealed trait ParseError     extends Product with Serializable
case object RollParserError extends ParseError
