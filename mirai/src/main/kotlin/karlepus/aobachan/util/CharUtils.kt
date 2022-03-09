package karlepus.aobachan.util

/**
 * @return 汉字的字符长度。
 */
public fun Char.chineseLength(): Int {
    return when (this) {
        in '\u0000'..'\u007F' -> 1
        in '\u0080'..'\u07FF' -> 2
        in '\u0800'..'\uFFFF' -> 2
        else -> 2
    }
}
