@file:Suppress("unused", "SpellCheckingInspection")

package io.karlepus.aobachan.mc.mcmod

import io.karlepus.aobachan.api.http
import io.ktor.client.request.*
import org.jsoup.Jsoup

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

    private lateinit var html: String
    private val totals: Int
    private val pages: Int
    private val mcmodResult: McmodSearchResult


    init {
        suspend { html = http.get(url) } // 初始化得到 html 的内容
        val titles: MutableList<String> = mutableListOf()
        val results: MutableList<String> = mutableListOf()
        val links: MutableList<String> = mutableListOf()
        val docs = Jsoup.parseBodyFragment(html)
        docs.getElementsByClass("result-item").forEach { res ->
            val head: String = res.getElementsByClass("head")[0].text() // 标题
            titles.add(head)
            val body: String = res.getElementsByClass("body")[0].text() // 简介主体 对该资源的简介内容
            results.add(body)
            val foot: String = res.getElementsByClass("foot")[0].text() // 脚部文字 来源、日期等信息
            val address: String? = Regex("""(?<=地址：)[\w./-]+(?! 快照时间：)""").find(foot)?.value
            val link = "https://$address" // 资源跳转链接
            links.add(link)
        }
        // 查询到的 结果总数 和 页面总数 信息
        val pageInfo: String = docs.getElementsMatchingOwnText("""找到约 \d+ 条结果，共约 \d+ 页。""")[0].text()
        val numbers: MutableList<String> = mutableListOf()
        Regex("""\d+""").findAll(pageInfo).forEach { numbers.add(it.value) }
        totals = numbers[0].toInt()
        pages = numbers[1].toInt()
        mcmodResult = McmodSearchResult(titles, results, links, totals, pages) // 给 mcmod 赋值
    }
}
