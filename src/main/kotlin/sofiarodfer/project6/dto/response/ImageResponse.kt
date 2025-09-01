package sofiarodfer.project6.dto.response

data class ImageResponse(
    val id: Long,
    val identifierTag: String,
    val altText: String,
    val visible: Boolean,
    val pageId: Long,
    val url: String
)