package io.github.ruixuantan.rocknroll.core.results

case class Result(
    result: Int,
    expected: Double,
    variance: Double,
    lowerBound: Int,
    upperBound: Int,
    diceRolled: Int,
)
