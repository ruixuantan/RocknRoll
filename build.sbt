name := "rocknroll"

ThisBuild / version := "2.0.0"
ThisBuild / organization := "io.github.ruixuantan"
ThisBuild / scalaVersion := "2.13.6"

lazy val settings = Seq(
  scalacOptions ++= Seq(
    "-language:higherKinds",
  ),
)

lazy val dependencies = new {
  val scalaCheckV = "1.15.4"
  val scalaCheck  = "org.scalacheck" %% "scalacheck" % scalaCheckV % Test

  val scalaTestV = "3.2.9"
  val scalaTest  = "org.scalatest" %% "scalatest" % scalaTestV % Test

  val catsV = "2.3.0"
  val cats  = "org.typelevel" %% "cats-core" % catsV

  val logbackV = "1.2.3"
  val logback  = "ch.qos.logback" % "logback-classic" % logbackV

  val circeV       = "0.13.0"
  val circeConfigV = "0.8.0"
  val circeCore    = "io.circe" %% "circe-core"    % circeV
  val circeGeneric = "io.circe" %% "circe-generic" % circeV
  val circeParser  = "io.circe" %% "circe-parser"  % circeV
  val circeConfig  = "io.circe" %% "circe-config"  % circeConfigV

  val http4sV           = "0.21.21"
  val http4sDsl         = "org.http4s" %% "http4s-dsl"          % http4sV
  val http4sBlazeServer = "org.http4s" %% "http4s-blaze-server" % http4sV
  val http4sBlazeClient = "org.http4s" %% "http4s-blaze-client" % http4sV
  val http4sCirce       = "org.http4s" %% "http4s-circe"        % http4sV
  val http4sServer      = "org.http4s" %% "http4s-server"       % http4sV

  val doobieV      = "0.13.3"
  val doobieCore   = "org.tpolecat" %% "doobie-core"      % doobieV
  val doobieH2     = "org.tpolecat" %% "doobie-h2"        % doobieV
  val doobieTest   = "org.tpolecat" %% "doobie-scalatest" % doobieV
  val doobieHikari = "org.tpolecat" %% "doobie-hikari"    % doobieV
  val doobiePg     = "org.tpolecat" %% "doobie-postgres"  % doobieV

  val flywayV = "7.9.1"
  val flyway  = "org.flywaydb" % "flyway-core" % flywayV

  val scalaJV = "2.4.2"
  val scalaJ  = "org.scalaj" %% "scalaj-http" % scalaJV
}

lazy val commonDependencies = Seq(
  dependencies.cats,
  dependencies.scalaTest,
  dependencies.scalaCheck,
  dependencies.logback,
)

lazy val global = (project in file("."))
  .settings(settings)
  .aggregate(rocknrollServer, rocknrollCore)

lazy val rocknrollCore = (project in file("rocknroll-core"))
  .settings(settings, libraryDependencies ++= commonDependencies)

lazy val rocknrollServer = (project in file("rocknroll-server"))
  .dependsOn(rocknrollCore)
  .settings(
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.http4sDsl,
      dependencies.http4sBlazeClient,
      dependencies.http4sBlazeServer,
      dependencies.http4sCirce,
      dependencies.http4sServer,
      dependencies.circeGeneric,
      dependencies.circeCore,
      dependencies.circeParser,
      dependencies.circeConfig,
      dependencies.doobieCore,
      dependencies.doobieH2,
      dependencies.doobieTest,
      dependencies.doobieHikari,
      dependencies.doobiePg,
      dependencies.flyway,
      dependencies.scalaJ,
    ),
  )
