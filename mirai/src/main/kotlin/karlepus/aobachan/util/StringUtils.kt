package karlepus.aobachan.util

import kotlin.math.max

/**
 * 限制字符串的长度，超过部分将被替换为 `"..."` 拼接在要求的字符串长度后面。
 *
 * @return 调整长度之后的字符串内容。
 */
public fun String.truncate(lengthLimit: Int, replacement: String = "..."): String = buildString {
    var lengthSum = 0
    for (char in this@truncate) {
        lengthSum += char.chineseLength()
        if (lengthSum > lengthLimit) {
            append(replacement)
            return toString()
        } else append(char)
    }
}

/**
 * 模糊匹配字符串。
 *
 * @param target 被推算的字符串内容。
 * @return 匹配率。
 */
internal fun String.fuzzyMatchWith(target: String): Double {
    if (this == target) return 1.0
    var match = 0
    for (i in 0..max(this.lastIndex, target.lastIndex)) {
        val t: Char = target.getOrNull(match) ?: break
        if (t == this.getOrNull(i)) match++
    }
    val longerLength: Int = max(this.length, target.length)
    val shortLength: Int = Integer.min(this.length, target.length)
    return match.toDouble() / (longerLength + (shortLength - match))
}
