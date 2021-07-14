package io.github.ruixuantan.rocknroll.core.generators

class SimpleGenerator(seed: Int) extends Generator {
  override def nextInt(range: Int): (Int, Generator) =
    if (0 <= seed && seed < range) (seed + 1, new SimpleGenerator(seed + 1))
    else (1, new SimpleGenerator(1))
}

object SimpleGenerator {
  def apply(seed: Int): SimpleGenerator = new SimpleGenerator(seed)
}
