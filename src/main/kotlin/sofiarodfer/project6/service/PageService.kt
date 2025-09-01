package sofiarodfer.project6.service

import org.springframework.stereotype.Service
import sofiarodfer.project6.dto.PageDTO
import sofiarodfer.project6.entity.Page
import sofiarodfer.project6.mapper.toDTO
import sofiarodfer.project6.repository.PageRepository

@Service
class PageService(
    private val pageRepository: PageRepository
) {
    fun createPage(name: String): PageDTO {
        if (pageRepository.findByName(name).isPresent) {
            throw IllegalArgumentException("Page with name '$name' already exists.")
        }
        val page = pageRepository.save(Page(name = name))
        return page.toDTO()
    }

    fun findById(id: Long): PageDTO {
        return pageRepository.findById(id).orElseThrow { RuntimeException("Page not found") }.toDTO()
    }

    fun findByName(name: String): PageDTO {
        return pageRepository.findByName(name).orElseThrow { RuntimeException("Page not found") }.toDTO()
    }

    fun findAll(): List<PageDTO> {
        return pageRepository.findAll().map { page -> page.toDTO() }
    }

}