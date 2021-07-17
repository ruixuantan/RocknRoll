package io.github.ruixuantan.rocknroll.core.generators

class CyclicalGenerator(initSeed: Long) extends Generator {
  var seed: Int = initSeed.toInt

  override def getName: String = "cyclic"

  override def nextInt(range: Int): Int = {
    if (0 <= seed && seed < range) seed += 1
    else seed = 1
    seed
  }
}

object CyclicalGenerator {
  def apply(seed: Long): CyclicalGenerator = new CyclicalGenerator(seed)
}
