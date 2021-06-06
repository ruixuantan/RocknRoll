package io.github.ruixuantan.rocknroll.server.domain.dice

sealed trait DieResponse
case class ValidResponse(
    results: String,
    expected: String,
    probabilities: String,
) extends DieResponse
case class InvalidResponse(
    msg: String,
) extends DieResponse
