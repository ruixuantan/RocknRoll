package io.github.ruixuantan.rocknroll.server.config

final case class RocknRollConfig(
  server: ServerConfig,
  db: DbConfig,
  log: LogConfig
)