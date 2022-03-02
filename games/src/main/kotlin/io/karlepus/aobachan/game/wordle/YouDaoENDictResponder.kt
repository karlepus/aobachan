@file:Suppress("unused")

package io.karlepus.aobachan.game.wordle

@kotlinx.serialization.Serializable
internal data class YouDaoENDictResponder(
    val `data`: Data,
    val result: Result
) {
    @kotlinx.serialization.Serializable
    data class Data(
        val entries: List<Entry>,
        val language: String,
        val query: String,
        val type: String
    )

    @kotlinx.serialization.Serializable
    data class Result(
        val code: Int,
        val msg: String
    )

    @kotlinx.serialization.Serializable
    data class Entry(
        val entry: String,
        val explain: String
    )
}
