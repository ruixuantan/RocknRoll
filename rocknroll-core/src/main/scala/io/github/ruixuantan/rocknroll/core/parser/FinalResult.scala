package io.github.ruixuantan.rocknroll.core.parser

case class FinalResult(
    input: String,
    result: Int,
    expected: Double,
    standardDeviation: Double,
    lowerBound: Int,
    upperBound: Int,
    diceRolled: Int,
)
