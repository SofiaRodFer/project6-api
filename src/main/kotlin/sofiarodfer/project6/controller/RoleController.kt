package sofiarodfer.project6.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.dto.request.RoleRequest
import sofiarodfer.project6.dto.response.RoleResponse
import sofiarodfer.project6.mapper.RoleMapper
import sofiarodfer.project6.service.RoleService

@RestController
@RequestMapping("/admin/roles")
class RoleController(
    private val roleService: RoleService,
    private val roleMapper: RoleMapper
) {
    @GetMapping
    fun listRoles(): List<RoleResponse> {
        val roles = roleService.findAll()
        return roles.map(roleMapper::toResponse)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRole(@Valid @RequestBody request: RoleRequest): RoleResponse {
        val createdRole = roleService.createRole(request)
        return roleMapper.toResponse(createdRole)
    }

    @PutMapping("/{id}")
    fun updateRole(@Valid @PathVariable id: Long, @RequestBody request: RoleRequest): RoleResponse {
        val updatedRole = roleService.updateRole(id, request)
        return roleMapper.toResponse(updatedRole)
    }

}