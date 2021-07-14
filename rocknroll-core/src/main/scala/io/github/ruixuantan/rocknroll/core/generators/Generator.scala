package io.github.ruixuantan.rocknroll.core.generators

trait Generator {
  def nextInt(range: Int): (Int, Generator)
}
