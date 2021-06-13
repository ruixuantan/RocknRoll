package io.github.ruixuantan.rocknroll.server.domain.dice

sealed trait DieResponse
case class ValidResponse(
    results: String,
    expected: String,
) extends DieResponse
case class InvalidResponse(
    msg: String,
) extends DieResponse
case class ValidateResponse(
    isValid: Boolean,
    input: String,
) extends DieResponse
