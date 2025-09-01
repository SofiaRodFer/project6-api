package sofiarodfer.project6.mapper

import org.springframework.stereotype.Component
import sofiarodfer.project6.dto.TextDTO
import sofiarodfer.project6.dto.response.TextResponse
import sofiarodfer.project6.entity.Text

@Component
class TextMapper {
    fun toDTO(text: Text): TextDTO {
        return TextDTO(
            id = text.id,
            identifierTag = text.identifierTag,
            content = text.content,
            visible = text.visible,
            pageId = text.page.id
        )
    }

    fun toResponse(dto: TextDTO): TextResponse {
        return TextResponse(
            id = dto.id,
            identifierTag = dto.identifierTag,
            content = dto.content,
            visible = dto.visible,
            pageId = dto.pageId
        )
    }
}