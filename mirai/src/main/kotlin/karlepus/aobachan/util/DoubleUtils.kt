package karlepus.aobachan.util

/**
 * 保留小数点到 [i] 位。
 *
 * @param i 保留几位小数点。
 * @return 字符串型小数。
 */
public fun Double.toDecimalPlace(i: Int): String = "%.${i}f".format(this)
