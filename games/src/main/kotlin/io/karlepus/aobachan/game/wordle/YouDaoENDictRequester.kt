@file:Suppress("unused", "HttpUrlsUsage")

package io.karlepus.aobachan.game.wordle

import io.karlepus.aobachan.http.http
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * `有道词典` 英文单词请求器。
 *
 * @author KarLepus
 */
public object YouDaoENDictRequester {
    @Suppress("SpellCheckingInspection")
    private const val rs = "abcdefghijklmnopqrstuvwxyz"

    /**
     * 请求 `有道词典` 得到理论 `100` 个单词结果。
     */
    private suspend fun String.dict100s(): List<String> {
        val json: String =
            http.get(String.format("http://dict.youdao.com/suggest?q=%s&le=eng&num=100&doctype=json", this))
        val response: YouDaoENDictResponder = Json.decodeFromString(json)
        return filter(response) ?: error("no words found.")
    }

    /**
     * 过滤 `有道词典` 联想得到的单词结果集（假如存在结果，只留下单词长度为 `5` 和 `7` 的）。
     */
    private fun filter(res: YouDaoENDictResponder): List<String>? {
        if (res.result.code == 200) {
            val list: MutableList<String> = mutableListOf()
            res.data.entries.forEach {
                if (it.entry.length == 5 || it.entry.length == 7) list.add(it.entry)
            }
            return list
        }
        return null
    }

    /**
     * 随机从 `26` 个字母中抽取三个字母，从 `有道词典` 搜索匹配的单词集。
     */
    private fun random3Character(): String = buildString {
        for (i in 0..2) append(rs.random())
    }

    /**
     * 最终确认的来自 `有道词典` 的满足单词长度为 `5` 或 `7` 个字母的所有单词。
     */
    public suspend fun dict100s(): List<String> = random3Character().dict100s()

    /**
     * 得到日期时间（0000-00-00）。
     */
    public fun datetime(): String = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}
