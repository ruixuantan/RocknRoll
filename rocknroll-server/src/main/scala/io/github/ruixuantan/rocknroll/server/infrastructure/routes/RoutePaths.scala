package io.github.ruixuantan.rocknroll.server.infrastructure.routes

object RoutePaths {
  private val v1Prefix = "api/v1/"
  sealed trait RoutePathsV1 { val path: String }
  case object SpellRoutePath { val path: String = v1Prefix + "spells/" }
  case object DieRoutePath { val path: String = v1Prefix + "dice/"}
  case object EquipmentRoutePath { val path: String = v1Prefix + "equipment/"}
}
