package io.github.ruixuantan.rocknroll.server.services

import scalaj.http.Http

object HttpService {
  def post(url: String, body: String): Int =
    Http(url)
      .header("content-type", "application/json")
      .postData(body)
      .asString
      .code
}
