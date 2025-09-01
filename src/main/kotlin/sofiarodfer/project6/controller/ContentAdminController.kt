package sofiarodfer.project6.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import sofiarodfer.project6.dto.response.ImageResponse
import sofiarodfer.project6.dto.response.TextResponse
import sofiarodfer.project6.mapper.TextMapper
import sofiarodfer.project6.mapper.toResponse
import sofiarodfer.project6.service.ImageService
import sofiarodfer.project6.service.TextService

@RestController
@RequestMapping("/admin/content")
class ContentAdminController(
    private val textService: TextService,
    private val imageService: ImageService,
    private val textMapper: TextMapper
) {
    @PostMapping("/texts")
    @ResponseStatus(HttpStatus.CREATED)
    fun createText(
        @RequestParam("identifierTag") identifierTag: String,
        @RequestParam("content") content: String,
        @RequestParam("pageId") pageId: Long
    ): TextResponse {
        val textDto = textService.createText(identifierTag, content, pageId)
        return textMapper.toResponse(textDto)
    }

    @PostMapping("/images")
    @ResponseStatus(HttpStatus.CREATED)
    fun uploadImage(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("altText") altText: String,
        @RequestParam("identifierTag") identifierTag: String,
        @RequestParam("pageId") pageId: Long
    ): ImageResponse {
        val imageDto = imageService.saveLocalImage(file, altText, identifierTag, pageId)
        return imageDto.toResponse()
    }
}