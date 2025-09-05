package sofiarodfer.project6.mapper

import sofiarodfer.project6.dto.TextDTO
import sofiarodfer.project6.dto.response.TextResponse
import sofiarodfer.project6.entity.Text

fun Text.toDTO() =
    TextDTO(
        id = this.id,
        identifierTag = this.identifierTag,
        content = this.content,
        visible = this.visible,
        pageId = this.page.id
    )

fun TextDTO.toResponse() =
    TextResponse(
        id = this.id,
        identifierTag = this.identifierTag,
        content = this.content,
        visible = this.visible,
        pageId = this.pageId
    )
