package sofiarodfer.project6.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.dto.response.PageResponse
import sofiarodfer.project6.mapper.toResponse
import sofiarodfer.project6.service.PageService

@RestController
@RequestMapping("/admin/pages")
class PageAdminController(
    private val pageService: PageService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPage(@RequestParam name: String): PageResponse {
        val pageDto = pageService.createPage(name)
        return pageDto.toResponse()
    }

    @GetMapping("/all")
    fun listPages(): List<PageResponse> {
        val pageDTOs = pageService.findAll()
        return pageDTOs.map { page -> page.toResponse() }
    }

    @GetMapping
    fun findByName(@RequestParam name: String): PageResponse {
        val pageDTO = pageService.findByName(name)
        return pageDTO.toResponse()
    }
}