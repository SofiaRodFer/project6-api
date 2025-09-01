package sofiarodfer.project6.controller

import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.dto.response.ImageResponse
import sofiarodfer.project6.dto.response.TextResponse
import sofiarodfer.project6.mapper.TextMapper
import sofiarodfer.project6.mapper.toResponse
import sofiarodfer.project6.service.ImageService
import sofiarodfer.project6.service.TextService

@RestController
@RequestMapping("/content")
class ContentController(
    private val textService: TextService,
    private val imageService: ImageService,
    private val textMapper: TextMapper,
) {
    @GetMapping("/texts/{identifierTag}")
    fun getText(@PathVariable identifierTag: String): TextResponse {
        val textDto = textService.findByIdentifierTag(identifierTag)
        return textMapper.toResponse(textDto)
    }

    @GetMapping("/pages/{pageId}/texts")
    fun getTextsForPage(@PathVariable pageId: Long): List<TextResponse> {
        val textDTOs = textService.findTextsByPageId(pageId)
        return textDTOs.map(textMapper::toResponse)
    }

    @GetMapping("/pages/{pageId}/images")
    fun getImagesForPage(@PathVariable pageId: Long): List<ImageResponse> {
        val imageDTOs = imageService.findImagesByPageId(pageId)
        return imageDTOs.map{ img -> img.toResponse() }
    }

    @GetMapping("/images/{identifierTag}")
    fun getImage(@PathVariable identifierTag: String): ResponseEntity<Resource> {
        val (imageDto, resource) = imageService.findAndLoadImage(identifierTag)

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(imageDto.contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"${imageDto.fileName}\"")
            .body(resource)
    }
}