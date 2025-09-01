package sofiarodfer.project6.repository

import org.springframework.data.jpa.repository.JpaRepository
import sofiarodfer.project6.entity.Page
import java.util.Optional

interface PageRepository : JpaRepository<Page, Long> {
    fun findByName(name: String): Optional<Page>
}