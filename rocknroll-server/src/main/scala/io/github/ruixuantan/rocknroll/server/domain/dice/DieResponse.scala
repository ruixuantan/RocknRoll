package io.github.ruixuantan.rocknroll.server.domain.dice

sealed trait DieResponse
case class ValidResponse(
    results: Int,
    expected: Double,
) extends DieResponse
case class InvalidResponse(
    msg: String,
) extends DieResponse
