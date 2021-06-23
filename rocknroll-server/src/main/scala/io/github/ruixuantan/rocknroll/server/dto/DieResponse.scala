package io.github.ruixuantan.rocknroll.server.dto

sealed trait DieResponse
case class ValidResponse(
    results: String,
    expected: String,
    standardDeviation: String,
) extends DieResponse
case class InvalidResponse(
    msg: String,
) extends DieResponse
case class ValidateResponse(
    isValid: Boolean,
    input: String,
) extends DieResponse
