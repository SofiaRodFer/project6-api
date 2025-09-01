package sofiarodfer.project6.mapper

import sofiarodfer.project6.dto.ImageDTO
import sofiarodfer.project6.dto.response.ImageResponse
import sofiarodfer.project6.entity.Image

fun Image.toDTO() =
    ImageDTO(
        id = this.id,
        identifierTag = this.identifierTag,
        fileName = this.fileName,
        contentType = this.contentType,
        altText = this.altText,
        visible = this.visible,
        pageId = this.page.id
    )


fun ImageDTO.toResponse() =
    ImageResponse(
        id = this.id,
        identifierTag = this.identifierTag,
        altText = this.altText,
        visible = this.visible,
        pageId = this.pageId,
        url = "/content/images/${this.identifierTag}"
    )
