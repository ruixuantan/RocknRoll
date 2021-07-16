package io.github.ruixuantan.rocknroll.server.models

case class Results(
    id: Int = 0,
    input: String,
    result: String,
    generator: String,
    createdAt: String = "",
)
