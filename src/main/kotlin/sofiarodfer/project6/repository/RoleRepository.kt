package sofiarodfer.project6.repository

import org.springframework.data.jpa.repository.JpaRepository
import sofiarodfer.project6.entity.Role
import java.util.Optional

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Optional<Role>
}