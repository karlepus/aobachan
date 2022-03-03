@file:JvmName("KTest")

import io.karlepus.aobachan.api.http
import io.ktor.client.request.*
import org.jsoup.Jsoup

suspend fun main() {
    val html: String = http.get("https://search.mcmod.cn/s?key=et2&site=&filter=0&mold=1")
    val doc = Jsoup.parseBodyFragment(html)
    doc.getElementsByClass("result-item").eachText().forEach {
        println("${it.substring(0, it.lastIndexOf("地址："))}\n")
    }
}
