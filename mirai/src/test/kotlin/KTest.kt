@file:JvmName("KTest")

import karlepus.aobachan.api.http
import io.ktor.client.request.*
import org.jsoup.Jsoup

suspend fun main() {
    val html: String = http.get("https://search.mcmod.cn/s?key=et2&site=&filter=0&mold=1")
    val doc = Jsoup.parseBodyFragment(html)
    doc.getElementsByClass("result-item").forEach { res ->
        val head: String = res.getElementsByClass("head")[0].text() // 标题
        val body: String = res.getElementsByClass("body")[0].text() // 简介主体 对该资源的简介内容
        val foot: String = res.getElementsByClass("foot")[0].text() // 脚部文字 来源、日期等信息
        val link = "https://${Regex("""(?<=地址：)[\w./-]+(?! 快照时间：)""").find(foot)?.value}" // 资源跳转链接
        println(buildString {
            append("标题：$head\n")
            append("内容：$body\n")
            append("跳转链接：$link\n\n")
        })
    }
    println(doc.getElementsMatchingOwnText("""找到约 \d+ 条结果，共约 \d+ 页。""")[0].text())
}
