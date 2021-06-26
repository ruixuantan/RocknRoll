package io.github.ruixuantan.rocknroll.core.utils

object MathUtil {
  def round(input: Double, dp: Int): Double =
    BigDecimal(input).setScale(dp, BigDecimal.RoundingMode.HALF_UP).toDouble
}
