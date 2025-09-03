package sofiarodfer.project6.dto

import sofiarodfer.project6.entity.Role

data class UserDTO(
    val id: Long,
    val username: String,
    val enabled: Boolean,
    val roles: Set<Role>
)