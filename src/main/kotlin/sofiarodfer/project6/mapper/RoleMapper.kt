package sofiarodfer.project6.mapper

import org.springframework.stereotype.Component
import sofiarodfer.project6.dto.request.RoleRequest
import sofiarodfer.project6.dto.response.RoleResponse
import sofiarodfer.project6.entity.Role

@Component
class RoleMapper {

    fun toEntity(request: RoleRequest): Role {
        return Role(
            name = request.name.uppercase().replace(" ", "_")
        )
    }

    fun toResponse(role: Role): RoleResponse {
        return RoleResponse(
            id = role.id,
            name = role.name
        )
    }
}