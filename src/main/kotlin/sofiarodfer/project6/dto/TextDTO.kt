package sofiarodfer.project6.dto

data class TextDTO(
    val id: Long,
    val identifierTag: String,
    val content: String,
    val visible: Boolean,
    val pageId: Long
)