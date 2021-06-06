package io.github.ruixuantan.rocknroll.core.parser

import scala.util.matching.Regex

sealed trait ParserSyntaxEnum { val syntax: Regex }
case object InvalidParser extends ParserSyntaxEnum {
  val syntax: Regex = """(?s).*""".r
}
case object RollParser extends ParserSyntaxEnum {
  val syntax: Regex = """((\d{0,2})d\d+\s)*(\d{0,2})d\d+""".r
}
