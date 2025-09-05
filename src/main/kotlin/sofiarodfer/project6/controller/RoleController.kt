package sofiarodfer.project6.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.dto.request.RoleRequest
import sofiarodfer.project6.dto.response.RoleResponse
import sofiarodfer.project6.mapper.toResponse
import sofiarodfer.project6.service.RoleService

@RestController
@RequestMapping("/admin/roles")
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping
    fun listRoles() =
        roleService.findAll().map { it.toResponse() }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRole(@Valid @RequestBody request: RoleRequest) =
        roleService.createRole(request).toResponse()

    @PutMapping("/{id}")
    fun updateRole(@Valid @PathVariable id: Long, @RequestBody request: RoleRequest) =
        roleService.updateRole(id, request).toResponse()

}