package sofiarodfer.project6.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.mapper.toResponse
import sofiarodfer.project6.service.PageService

@RestController
@RequestMapping("/admin/pages")
class PageAdminController(
    private val pageService: PageService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPage(@RequestParam name: String) =
        pageService.createPage(name).toResponse()

    @GetMapping("/all")
    fun listPages() =
        pageService.findAll().map{ it.toResponse() }

    @GetMapping
    fun findByName(@RequestParam name: String) =
        pageService.findByName(name).toResponse()

}