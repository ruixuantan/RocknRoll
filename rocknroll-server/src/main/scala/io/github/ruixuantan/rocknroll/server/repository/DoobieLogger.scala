package io.github.ruixuantan.rocknroll.server.repository

import doobie.LogHandler
import doobie.util.log.{ExecFailure, ProcessingFailure, Success}

import java.util.logging.Logger

object DoobieLogger {
  val logger: LogHandler = {
    val jdkLogger = Logger.getLogger(getClass.getName)
    LogHandler {
      case Success(s, a, e1, e2) =>
        jdkLogger.info(s"""Successful Statement Execution:
                          |
                          |  ${s.linesIterator
          .dropWhile(_.trim.isEmpty)
          .mkString("\n  ")}
                          | arguments = [${a.mkString(", ")}]
                          |   elapsed = ${e1.toMillis} ms exec + ${e2.toMillis} ms processing (${(e1 + e2).toMillis} ms total)
      """.stripMargin)

      case ProcessingFailure(s, a, e1, e2, t) =>
        jdkLogger.severe(s"""Failed Resultset Processing:
                            |  ${s.linesIterator
          .dropWhile(_.trim.isEmpty)
          .mkString("\n  ")}
                            |
                            | arguments = [${a.mkString(", ")}]
                            |   elapsed = ${e1.toMillis} ms exec + ${e2.toMillis} ms processing (failed) (${(e1 + e2).toMillis} ms
                            |   failure = ${t.getMessage}
      """.stripMargin)

      case ExecFailure(s, a, e1, t) =>
        jdkLogger.severe(s"""Failed StatementExecution:
                            |
                            |  ${s.linesIterator
          .dropWhile(_.trim.isEmpty)
          .mkString("\n  ")}
                            |
                            | arguments = [${a.mkString(", ")}]
                            |   elapsed = ${e1.toMillis} ms exec (failed)
                            |   failure = ${t.getMessage}
      """.stripMargin)

    }
  }
}
