package io.github.ruixuantan.rocknroll.core.generators

class TestGenerator extends Generator {
  override def nextInt(range: Int): (Int, Generator) = (1, TestGenerator())
}

object TestGenerator {
  def apply(): TestGenerator = new TestGenerator()
}
