package sofiarodfer.project6.repository

import org.springframework.data.jpa.repository.JpaRepository
import sofiarodfer.project6.entity.Text
import java.util.Optional

interface TextRepository : JpaRepository<Text, Long> {
    fun findByIdentifierTag(identifierTag: String): Optional<Text>
    fun findByPageId(pageId: Long): List<Text>
}