@file:Suppress("unused", "SpellCheckingInspection")

package io.karlepus.aobachan.mc.mcmod

/**
 * [MC百科|最大的Minecraft中文MOD百科](https://www.mcmod.cn/) 请求器。
 *
 * - [filter] 类型过滤器具有如下定义：
 *     - [0] -> 全部；
 *     - [1] -> 模组；
 *     - [2] -> 整合包；
 *     - [3] -> 资料；
 *     - [4] -> 教程；
 *     - [5] -> 作者；
 *     - [6] -> 用户；
 *     - [7] -> 社群；
 *     - [8] -> 服务器。
 *
 * @property key 关键词。
 * @property filter 类型过滤器。
 * @property page 页数。
 * @author KarLepus
 */
internal class McmodRequester(
    private val key: String,
    private val filter: Int = 0,
    private var page: Int = 1
) {
    /**
     * `"mold = 1"` 表示使用复杂搜索，发起 `HTTP GET` 请求的链接，通过 [String.format] 动态赋值得到最终链接。
     */
    private val url: String
        get() = String.format("https://search.mcmod.cn/s?key=%s&filter=%d&mold=1&page=%d", key, filter, page)

    /**
     * 由 `重生` 提供的已知 `API` 接口，通过 [String.format] 动态赋值得到最终链接。
     */
    private val api: String
        get() = String.format("https://api.mcmod.cn/search/?%s", key)

    // 1. https://search.mcmod.cn/s?key=<关键词>&filter=<搜索过滤>&mold=<简单/复杂搜索>&page=[页数]
    //    e.g. https://search.mcmod.cn/s?key=jei&filter=1&mold=1&page=1
    //         关键词=jei 过滤器=模组 搜索模式=复杂 页数=1
    //         返回所有搜索结果的完整 HTML 页面代码
    //
    // 2. https://api.mcmod.cn/search/?key=<关键词>&qq=[消息发送人QQ]&qqg=[发送人所在群号]
    //    e.g. https://api.mcmod.cn/search/?key=jei&qq=1598651543&qqg=641188270
    //         关键词=jei 消息发送人=1598651543 发送人所在的群=641188270
    //         返回一个 @1598651543 的消息一样的字符串，包含搜索结果的标题和原链接
    //    n.b. 使用过程中将会无视 qq 和 qqg 参数

    fun violence() {}
}
