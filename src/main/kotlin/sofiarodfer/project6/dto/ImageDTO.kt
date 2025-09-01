package sofiarodfer.project6.dto

data class ImageDTO(
    val id: Long,
    val identifierTag: String,
    val fileName: String,
    val contentType: String,
    val altText: String,
    val visible: Boolean,
    val pageId: Long
)