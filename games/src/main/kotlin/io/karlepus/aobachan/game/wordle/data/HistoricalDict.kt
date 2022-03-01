@file:Suppress("SpellCheckingInspection")

package io.karlepus.aobachan.game.wordle.data

/**
 * Wordle 历史词汇记录
 */
@kotlinx.serialization.Serializable
public data class HistoricalDict(
    /**
     *  期数（ `"00001"` ）
     */
    val issue: String,
    /**
     * 本期词汇
     */
    val dict: String,
    /**
     * 词汇长度（ `5` `7` ）
     */
    val length: Int,
    /**
     * 日期（ `"yyyy-MM-dd"` ）
     */
    val date: String,
    /**
     * 回答正确人数
     */
    val correct: Int,
    /**
     * 回答错误人数
     */
    val wrong: Int,
    /**
     * 总参与人数
     */
    val subscriber: Int,
    /**
     * 正确率（精确到 `0.00` ，即 `"00%"` ）
     */
    val rate: String
)
