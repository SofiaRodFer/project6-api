package sofiarodfer.project6.controller

import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.mapper.toResponse
import sofiarodfer.project6.service.ImageService
import sofiarodfer.project6.service.TextService

@RestController
@RequestMapping("/content")
class ContentController(
    private val textService: TextService,
    private val imageService: ImageService
) {
    @GetMapping("/texts/{identifierTag}")
    fun getText(@PathVariable identifierTag: String) =
        textService.findByIdentifierTag(identifierTag).toResponse()

    @GetMapping("/pages/{pageId}/texts")
    fun getTextsForPage(@PathVariable pageId: Long) =
        textService.findTextsByPageId(pageId).map { it.toResponse() }

    @GetMapping("/pages/{pageId}/images")
    fun getImagesForPage(@PathVariable pageId: Long) =
        imageService.findImagesByPageId(pageId).map { it.toResponse() }

    @GetMapping("/images/{identifierTag}")
    fun getImage(@PathVariable identifierTag: String): ResponseEntity<Resource> {
        val (imageDTO, resource) = imageService.findAndLoadImage(identifierTag)

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(imageDTO.contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"${imageDTO.fileName}\"")
            .body(resource)
    }
}