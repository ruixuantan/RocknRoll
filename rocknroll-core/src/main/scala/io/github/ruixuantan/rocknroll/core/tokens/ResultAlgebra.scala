package io.github.ruixuantan.rocknroll.core.tokens

trait ResultAlgebra {
  def identity(): Result

  def getResult(value: Value): Result

  def add(x: Result)(y: Result): Result

  def subtract(x: Result)(y: Result): Result
}
