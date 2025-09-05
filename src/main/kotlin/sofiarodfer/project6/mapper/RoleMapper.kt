package sofiarodfer.project6.mapper

import sofiarodfer.project6.dto.RoleDTO
import sofiarodfer.project6.dto.request.RoleRequest
import sofiarodfer.project6.dto.response.RoleResponse
import sofiarodfer.project6.entity.Role

fun RoleRequest.toEntity() =
    Role(
        name = this.name.uppercase().replace(" ", "_")
    )

fun Role.toResponse() =
    RoleResponse(
        id = this.id,
        name = this.name
    )

fun RoleDTO.toResponse() =
    RoleResponse(
        id = this.id,
        name = this.name
    )

fun Role.toDTO() =
    RoleDTO(
        id = this.id,
        name = this.name
    )
