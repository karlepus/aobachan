@file:Suppress("unused", "SpellCheckingInspection")

package io.karlepus.aobachan.mc.mcmod

/**
 * [MC百科|最大的Minecraft中文MOD百科](https://www.mcmod.cn/) 请求器。
 *
 * @property key 关键词。
 * @property filter 类型过滤器。
 * @property page 页数。
 */
internal class McmodRequester(
    private val key: String,
    private val filter: Int = 0,
    private var page: Int = 1
) {
    /**
     * `"mold = 1"` 表示使用复杂搜索，发起 `HTTP GET` 请求的链接，通过 [String.format] 动态赋值得到最终链接。
     */
    private val url: String =
        String.format("https://search.mcmod.cn/s?key=%s&filter=%d&mold=1&page=%d", key, filter, page)
}
