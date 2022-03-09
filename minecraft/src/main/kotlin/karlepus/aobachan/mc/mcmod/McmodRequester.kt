@file:Suppress("unused", "SpellCheckingInspection", "MemberVisibilityCanBePrivate")

package karlepus.aobachan.mc.mcmod

import karlepus.aobachan.api.http
import io.ktor.client.request.*
import org.jsoup.Jsoup

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
 * @author KarLepus
 */
public class McmodRequester(
    private val key: String,
    private val filter: Int = 0
) {
    /**
     * 页数。
     */
    private var page: Int = 1

    /**
     * `"mold = 1"` 表示使用复杂搜索，发起 `HTTP GET` 请求的链接，通过 [String.format] 动态赋值得到最终链接。
     */
    private val url: String
        get() = String.format("https://search.mcmod.cn/s?key=%s&filter=%d&mold=0&page=%d", key, filter, page)

    /**
     * 由 ***模组百科站长 -- 重生*** 提供的已知 `API` 接口，通过 [String.format] 动态赋值得到最终链接。
     */
    private val api: String
        get() = String.format("https://api.mcmod.cn/search/?key=%s", key)

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // 暴力爬取（获取较为详细的内容）
    //
    // 1. https://search.mcmod.cn/s?key=<关键词>&filter=<搜索过滤>&mold=<简单/复杂搜索>&page=[页数]
    //    e.g. https://search.mcmod.cn/s?key=jei&filter=1&mold=1&page=1
    //         关键词=jei 过滤器=模组 搜索模式=复杂 页数=1
    //         返回所有搜索结果的完整 HTML 页面代码
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // 官方接口（适合简单的引导网站链接）
    //
    // 2. https://api.mcmod.cn/search/?key=<关键词>&qq=[消息发送人QQ]&qqg=[发送人所在群号]
    //    e.g. https://api.mcmod.cn/search/?key=jei&qq=1598651543&qqg=641188270
    //         关键词=jei 消息发送人=1598651543 发送人所在的群=641188270
    //         返回一个 @1598651543 的消息一样的字符串，包含搜索结果的标题和原链接
    //    n.b. 使用过程中将会无视 qq 和 qqg 参数
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     *  暴力爬取得到的整个 `html` 源码的数据缓存域。
     */
    private lateinit var html: String

    /**
     * 使用 `API` 得到的结果数据缓存域（主要是标题和链接）。
     */
    private lateinit var basic: String

    /**
     * 暴力爬取所有搜索结果。
     */
    public suspend fun violence(): McmodRequester = apply {
        html = http.get(url)
    }

    /**
     * 公共接口获得搜索结果。
     */
    public suspend fun official(): McmodRequester = apply {
        basic = http.get(api)
    }

    /**
     * @return 请求到的源码，未调用 [violence] 则为空。
     */
    public fun source(): String = if (this::html.isInitialized) html else ""

    /**
     * @return 请求到的字符串，未调用 [official] 则为空。
     */
    public fun string(): String = if (this::basic.isInitialized) basic else ""

    /**
     * 序列化爬取的网站内容（不进行任何可能发生的异常的处理）。
     *
     * 可能的异常为 [IndexOutOfBoundsException] 。
     *
     * @return [McmodResponder] 数据类。
     */
    public fun serialize(): McmodResponder? = if (this::html.isInitialized) {
        val doc = Jsoup.parseBodyFragment(html)
        val results: MutableList<McmodResponder.Result> = mutableListOf()
        doc.getElementsByClass("result-item").forEach { element ->
            val head: String = element.getElementsByClass("head")[0].text() // 标题
            val body: String = element.getElementsByClass("body")[0].text() // 简介
            val foot: String = element.getElementsByClass("foot")[0].text() // 脚部信息
            val link = "https://${Regex("""(?<=地址：)[\w./-]+(?! 快照时间：)""").find(foot)?.value}"
            val update: String = Regex("""(?<=快照时间：)[\d-]+(?! 来自：)""").find(foot)?.value ?: ""
            results.add(McmodResponder.Result(head, body, link, update))
        }
        doc.getElementsByClass("search-result")[0].getElementsByTag("p")[0].text().let { res ->
            Regex("""[0-9]+""").findAll(res).let {
                val totals: Int = it.elementAt(0).value.toInt()
                val pages: Int = it.elementAt(1).value.toInt()
                McmodResponder(results, totals, pages)
            }
        }
    } else null
}
