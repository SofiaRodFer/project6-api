package sofiarodfer.project6.repository

import org.springframework.data.jpa.repository.JpaRepository
import sofiarodfer.project6.entity.Account
import java.util.*

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByUserId(userId: Long): Optional<Account>
}