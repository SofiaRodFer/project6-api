package sofiarodfer.project6.service

import org.springframework.stereotype.Service
import sofiarodfer.project6.dto.TextDTO
import sofiarodfer.project6.entity.Text
import sofiarodfer.project6.mapper.toDTO
import sofiarodfer.project6.mapper.toEntity
import sofiarodfer.project6.repository.TextRepository

@Service
class TextService(
    private val textRepository: TextRepository,
    private val pageService: PageService
) {
    fun createText(identifierTag: String, content: String, pageId: Long): TextDTO {
        if (textRepository.findByIdentifierTag(identifierTag).isPresent) {
            throw IllegalArgumentException("Text with identifier '$identifierTag' already exists.")
        }
        val page = pageService.findById(pageId)
        val text = Text(
            identifierTag = identifierTag,
            content = content,
            visible = true,
            page = page.toEntity()
        )
        return textRepository.save(text).toDTO()
    }

    fun findTextsByPageId(pageId: Long) =
        textRepository.findByPageId(pageId).map { it.toDTO() }

    fun findByIdentifierTag(identifierTag: String): TextDTO {
        val text = textRepository.findByIdentifierTag(identifierTag)
            .orElseThrow { RuntimeException("Text not found with tag: $identifierTag") }
        return text.toDTO()
    }

}