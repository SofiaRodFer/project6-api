package sofiarodfer.project6.repository

import org.springframework.data.jpa.repository.JpaRepository
import sofiarodfer.project6.entity.User
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>

    fun findByRoles_Id(roleId: Long): List<User>
}