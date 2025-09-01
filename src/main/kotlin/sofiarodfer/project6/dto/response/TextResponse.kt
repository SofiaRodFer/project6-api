package sofiarodfer.project6.dto.response

data class TextResponse(
    val id: Long,
    val identifierTag: String,
    val content: String,
    val visible: Boolean,
    val pageId: Long
)