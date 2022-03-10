package karlepus.aobachan.mc.teacon

@kotlinx.serialization.Serializable
public data class ChaHouTanData(
    val title: String,
    val link: String,
    val description: String,
    val image: String?,
    val publish: String,
    val issues: Int
)
