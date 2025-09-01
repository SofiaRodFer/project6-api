package sofiarodfer.project6.repository

import org.springframework.data.jpa.repository.JpaRepository
import sofiarodfer.project6.entity.Image
import java.util.Optional

interface ImageRepository : JpaRepository<Image, Long> {
    fun findByIdentifierTag(identifierTag: String): Optional<Image>
    fun findByPageId(pageId: Long): List<Image>
}