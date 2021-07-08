package io.github.ruixuantan.rocknroll.server.dto

import io.github.ruixuantan.rocknroll.core.parser.FinalResult

sealed trait DieResponse
case class ValidResponse(
    inputString: String,
    resultString: String,
    expected: String,
    standardDeviation: String,
    results: Array[FinalResult],
) extends DieResponse
case class InvalidResponse(
    msg: String,
) extends DieResponse
case class ValidateResponse(
    isValid: Boolean,
    input: String,
) extends DieResponse
