@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package karlepus.aobachan.mc.teacon

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import io.ktor.client.request.*
import karlepus.aobachan.api.http
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * [TeaCon 茶后谈](https://www.teacon.cn/chahoutan/) 订阅器。
 *
 * - [TeaCon 模组开发茶会](https://www.teacon.cn) 。
 *
 * @author KarLepus
 */
public class ChaHouTanSubscriber {
    /**
     *  `RSS` 订阅链接。
     */
    private val rssLink = "https://chahoutan.teacon.cn/feed"

    /**
     * 构造获取某一期茶后谈内容的链接。
     */
    private fun url(issue: Int): String = String.format("https://chahoutan.teacon.cn/v1/posts/%d", issue)

    /**
     * 获取标题中日期的 [Regex] 。
     */
    private val dateRegex = Regex("""(?<=（)[\d-]+(?! ）)""")

    /**
     * 得到当前的时间戳（ `"yyyy-MM-dd" "2022-01-01"` ）。
     */
    private val timestamp: String by lazy {
        ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    /**
     * 通过 RSS 订阅得到的最新一期的茶后谈数据域（需要调用 [latest] 方法）。
     */
    private lateinit var rss: SyndEntry

    /**
     * RSS 订阅茶后谈的最新一期。
     */
    public suspend fun latest(): ChaHouTanSubscriber = apply {
        rss = SyndFeedInput().build(XmlReader(http.get<InputStream>(rssLink))).entries.first()
    }

    /**
     * 获取当前最新期数。
     */
    public fun currentIssue(): Int = Regex("""\d+""").find(rss.title)?.value?.toInt() ?: 1

    /**
     * 回顾指定期数的茶后谈。
     *
     * @param issue 期数。
     */
    public suspend fun review(issue: Int): ChaHouTanResponder = Json.decodeFromString(http.get(url(issue)))

    /**
     * 尝试是否发布了新的更新。
     */
    public fun status(): Boolean = dateRegex.find(rss.title)?.value == timestamp
}
