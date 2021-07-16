package io.github.ruixuantan.rocknroll.core.generators

class TestGenerator extends Generator {
  override def getName: String = "test"

  override def nextInt(range: Int): Int = 1
}

object TestGenerator {
  def apply(): TestGenerator = new TestGenerator()
}
