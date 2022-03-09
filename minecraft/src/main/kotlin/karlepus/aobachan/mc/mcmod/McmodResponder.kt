@file:Suppress("unused")

package karlepus.aobachan.mc.mcmod

/**
 * [MC百科|最大的Minecraft中文MOD百科](https://www.mcmod.cn/) 请求的响应数据类。
 *
 * @param results 结果集。
 * @param totals 总结果数。
 * @param pages 总页数。
 */
@kotlinx.serialization.Serializable
public data class McmodResponder(
    val results: List<Result>,
    val totals: Int,
    val pages: Int
) {
    /**
     * 单个搜索结果所包含的内容。
     *
     * @param head 标题？
     * @param body 简介？
     * @param link 链接。
     * @param update 最后一次更新时间（ `"yyyy-MM-dd"` ）。
     */
    @kotlinx.serialization.Serializable
    public data class Result(
        val head: String,
        val body: String,
        val link: String,
        val update: String
    )
}
