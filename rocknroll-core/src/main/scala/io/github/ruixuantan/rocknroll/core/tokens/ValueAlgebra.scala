package io.github.ruixuantan.rocknroll.core.tokens

import io.github.ruixuantan.rocknroll.core.results.Result

trait ValueAlgebra[V] {
  def getResult(value: V): Result
}
