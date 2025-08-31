package sofiarodfer.project6.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sofiarodfer.project6.dto.UserDTO
import sofiarodfer.project6.dto.request.UserCreateRequest
import sofiarodfer.project6.dto.request.UserUpdateRequest
import sofiarodfer.project6.mapper.UserMapper
import sofiarodfer.project6.repository.RoleRepository
import sofiarodfer.project6.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val userMapper: UserMapper
) {

    fun findAll(): List<UserDTO> {
        return userRepository.findAll().map(userMapper::toDTO)
    }

    @Transactional
    fun createUser(request: UserCreateRequest): UserDTO {
        if (userRepository.findByUsername(request.username).isPresent) {
            throw IllegalArgumentException("Username ${request.username} already exists")
        }
        val roles = request.roles.map { roleName ->
            roleRepository.findByName(roleName)
                .orElseThrow { RuntimeException("Role not found: $roleName") }
        }.toSet()

        val userEntity = userMapper.toEntity(request, roles)
        val savedUser = userRepository.save(userEntity)

        return userMapper.toDTO(savedUser)
    }

    @Transactional
    fun updateUser(id: Long, request: UserUpdateRequest): UserDTO {
        val currentUser = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found with id: $id") }

        val newRoles = request.roles?.map { roleName ->
            roleRepository.findByName(roleName)
                .orElseThrow { RuntimeException("Role not found: $roleName") }
        }?.toSet()

        val updatedAccount = currentUser.account?.let { currentAccount ->
            currentAccount.copy(
                firstName = request.firstName ?: currentAccount.firstName,
                lastName = request.lastName ?: currentAccount.lastName,
                cep = request.cep ?: currentAccount.cep
            )
        }

        val updatedUser = currentUser.copy(
            enabled = request.enabled ?: currentUser.enabled,
            roles = newRoles ?: currentUser.roles,
            account = updatedAccount
        )

        val savedUser = userRepository.save(updatedUser)
        return userMapper.toDTO(savedUser)
    }
}