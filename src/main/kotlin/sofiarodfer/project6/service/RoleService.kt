package sofiarodfer.project6.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sofiarodfer.project6.config.properties.SecurityProperties
import sofiarodfer.project6.config.properties.findRoleByIdentifier
import sofiarodfer.project6.config.properties.isRoleDeletable
import sofiarodfer.project6.dto.RoleDTO
import sofiarodfer.project6.dto.request.RoleRequest
import sofiarodfer.project6.entity.Role
import sofiarodfer.project6.enum.RoleEnum
import sofiarodfer.project6.mapper.toDTO
import sofiarodfer.project6.mapper.toEntity
import sofiarodfer.project6.repository.RoleRepository
import sofiarodfer.project6.repository.UserRepository

@Service
class RoleService(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val securityProperties: SecurityProperties
) {

    fun findAll() =
        roleRepository.findAll().map { it.toDTO() }

    @Transactional
    fun createRole(request: RoleRequest): RoleDTO {
        val newName = request.name.uppercase().replace(" ", "_")
        if (roleRepository.findByName(newName).isPresent) {
            throw IllegalArgumentException("Role '$newName' already exists")
        }
        val roleEntity = request.toEntity()
        return roleRepository.save(roleEntity).toDTO()
    }

    @Transactional
    fun updateRole(id: Long, request: RoleRequest): RoleDTO {
        val currentRole = roleRepository.findById(id)
            .orElseThrow { RuntimeException("Role not found with id: $id") }

        val updatedRole = currentRole.copy(
            name = request.name.uppercase().replace(" ", "_")
        )
        return roleRepository.save(updatedRole).toDTO()
    }

    @Transactional
    fun deleteRole(id: Long) {
        val defaultRoleName = securityProperties.findRoleByIdentifier(RoleEnum.DEFAULT)
        val roleToDelete = roleRepository.findById(id).orElseThrow { RuntimeException("Role not found with id: $id") }
        if (!securityProperties.isRoleDeletable(roleToDelete.name)) {
            throw IllegalStateException("Cannot delete critical system role: ${roleToDelete.name}")
        }
        val defaultRole = roleRepository.findByName(defaultRoleName).orElseThrow {
            IllegalStateException("Critical role '$defaultRoleName' is missing from the database.")
        }
        val usersToUpdate = userRepository.findByRoles_Id(roleToDelete.id)
        usersToUpdate.forEach { user ->
            val updatedRoles = user.roles.toMutableSet()
            updatedRoles.remove(roleToDelete)
            if (updatedRoles.isEmpty()) {
                updatedRoles.add(defaultRole)
            }
            user.roles = updatedRoles
            userRepository.save(user)
        }
        roleRepository.delete(roleToDelete)
    }

}