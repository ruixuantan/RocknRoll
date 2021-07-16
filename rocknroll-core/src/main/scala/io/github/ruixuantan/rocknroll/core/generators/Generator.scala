package io.github.ruixuantan.rocknroll.core.generators

trait Generator {
  def getName: String

  def nextInt(range: Int): Int
}
