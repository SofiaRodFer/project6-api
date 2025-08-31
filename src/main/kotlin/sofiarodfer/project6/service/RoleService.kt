package sofiarodfer.project6.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sofiarodfer.project6.dto.request.RoleRequest
import sofiarodfer.project6.entity.Role
import sofiarodfer.project6.mapper.RoleMapper
import sofiarodfer.project6.repository.RoleRepository

@Service
class RoleService(
    private val roleRepository: RoleRepository,
    private val roleMapper: RoleMapper
) {

    fun findAll(): List<Role> {
        return roleRepository.findAll()
    }

    @Transactional
    fun createRole(request: RoleRequest): Role {
        val newName = request.name.uppercase().replace(" ", "_")
        if (roleRepository.findByName(newName).isPresent) {
            throw IllegalArgumentException("Role '$newName' already exists")
        }
        val roleEntity = roleMapper.toEntity(request)
        return roleRepository.save(roleEntity)
    }

    @Transactional
    fun updateRole(id: Long, request: RoleRequest): Role {
        val currentRole = roleRepository.findById(id)
            .orElseThrow { RuntimeException("Role not found with id: $id") }

        val updatedRole = currentRole.copy(
            name = request.name.uppercase().replace(" ", "_")
        )
        return roleRepository.save(updatedRole)
    }

}