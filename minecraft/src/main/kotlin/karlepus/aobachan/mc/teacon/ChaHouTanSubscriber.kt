@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package karlepus.aobachan.mc.teacon

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import io.ktor.client.request.*
import karlepus.aobachan.api.http
import java.io.InputStream
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
     * 获取的 RSS 缓存数据域，需要调用 [rss] 方法得到初始化。
     */
    private lateinit var rss: List<SyndEntry>

    /**
     * 得到当前的时间戳（ `"yyyy-MM-dd" "2022-01-01"` ）。
     */
    private val timestamp: String
        get() = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    /**
     * RSS 订阅茶后谈的更新。
     */
    public suspend fun rss() {
        val xml: InputStream = http.get(rssLink)
        val input = SyndFeedInput()
        val feed: SyndFeed = input.build(XmlReader(xml))
        rss = feed.entries
    }

    /**
     * 获取最近更新的茶后谈，当获取到茶后谈的最新一期所标注的日期与当日不相等时，返回 `null` 。
     */
    public fun update(): ChaHouTanData? = latest().apply {
        Regex("""[\w-]+""").find(title)?.value.let {
            if (it != null && it == timestamp) return null
        }
    }

    /**
     * 获取最新一期的茶后谈。
     */
    public fun latest(): ChaHouTanData = latestImpl(rss.first())

    /**
     * 获取最新一期茶后谈方法的内部实现。
     */
    private fun latestImpl(context: SyndEntry): ChaHouTanData {
        val title: String = context.title
        val link: String = context.link
        val description: String = context.description.value
        val rawContent: String = context.contents.first().value
        val image: String? = Regex("""(?<=src=")[\w./:]+(?! " alt)""").find(rawContent)?.value
        val publish: String = ZonedDateTime.ofInstant(
            context.publishedDate.toInstant(), ZoneId.of("GMT")
        ).format(DateTimeFormatter.ofPattern("yyyy年MM月dd月 EEE HH:mm:ss", Locale.CHINA))
        val issues: Int = Regex("""\d+""").findAll(title).first().value.toInt()
        return ChaHouTanData(title, link, description, image, publish, issues)
    }

    public suspend fun review(issue: Int): ChaHouTanData {
        val url: String = String.format("https://chahoutan.teacon.cn/v1/posts/%d", issue)
        val json: String = http.get(url)
    }
}
