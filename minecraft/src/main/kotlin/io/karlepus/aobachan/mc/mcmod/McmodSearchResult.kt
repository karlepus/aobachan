@file:Suppress("unused", "SpellCheckingInspection")

package io.karlepus.aobachan.mc.mcmod

@kotlinx.serialization.Serializable
internal data class McmodSearchResult(
    /**
     * 搜索结果集
     */
    val results: List<String>,
    /**
     * 结果总数
     */
    val totals: Int,
    /**
     * 总页数
     */
    val pages: Int,
    /**
     * 对应结果集的链接
     */
    val links: List<String>
)
