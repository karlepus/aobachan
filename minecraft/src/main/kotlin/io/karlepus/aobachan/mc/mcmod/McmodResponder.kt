@file:Suppress("unused")

package io.karlepus.aobachan.mc.mcmod

@kotlinx.serialization.Serializable
public data class McmodResponder(
    val results: List<Result>,
    val totals: Int,
    val pages: Int
) {
    @kotlinx.serialization.Serializable
    public data class Result(
        val head: String,
        val body: String,
        val link: String,
        val update: String
    )
}
