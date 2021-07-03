package io.github.ruixuantan.rocknroll.server.utils

import scala.concurrent.Future

case class ActionableResponse[T](
    dto: T,
    actions: List[() => Future[Unit]],
)
