package karlepus.aobachan.mc.teacon

@kotlinx.serialization.Serializable
public data class ChaHouTanResponder(
    val anchor_urls: List<String>,
    val anchors: List<String>,
    val editors: List<String>,
    val id: Int,
    val images: List<Image>,
    val publish_time: String,
    val revision: String,
    val revision_url: String,
    val text: String,
    val title: String,
    val type: String,
    val url: String
) {
    @kotlinx.serialization.Serializable
    public data class Image(
        val detached: Boolean,
        val id: String,
        val png: String,
        val png_url: String,
        val size: List<Int>,
        val webp: String,
        val webp_url: String
    )
}
