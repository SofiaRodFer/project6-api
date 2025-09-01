package sofiarodfer.project6.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sofiarodfer.project6.dto.UserDTO
import sofiarodfer.project6.dto.request.UserCreateRequest
import sofiarodfer.project6.dto.request.UserUpdateRequest
import sofiarodfer.project6.entity.Account
import sofiarodfer.project6.entity.User
import sofiarodfer.project6.mapper.UserMapper
import sofiarodfer.project6.repository.AccountRepository
import sofiarodfer.project6.repository.RoleRepository
import sofiarodfer.project6.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val accountRepository: AccountRepository,
    private val userMapper: UserMapper
) {

    fun findAll(): List<UserDTO> {
        return userRepository.findAll().map(userMapper::toDTO)
    }

    fun findById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found with id: $id") }
    }

    @Transactional
    fun createUser(request: UserCreateRequest): UserDTO {
        if (userRepository.findByUsername(request.username).isPresent) {
            throw IllegalArgumentException("Username ${request.username} already exists")
        }
        val roles = request.roles.map { roleName ->
            roleRepository.findByName(roleName).orElseThrow { RuntimeException("Role not found: $roleName") }
        }.toSet()
        val userEntity = userMapper.toEntity(request, roles)
        val savedUser = userRepository.save(userEntity)
        val accountEntity = Account(
            firstName = request.firstName,
            lastName = request.lastName,
            cep = request.cep,
            user = savedUser
        )
        accountRepository.save(accountEntity)
        return userMapper.toDTO(savedUser)
    }

    @Transactional
    fun updateUser(id: Long, request: UserUpdateRequest): UserDTO {
        val currentUser = findById(id)
        val newRoles = request.roles?.map { roleName ->
            roleRepository.findByName(roleName).orElseThrow { RuntimeException("Role not found: $roleName") }
        }?.toSet()
        val updatedUser = currentUser.copy(
            enabled = request.enabled ?: currentUser.enabled,
            roles = newRoles ?: currentUser.roles
        )
        val savedUser = userRepository.save(updatedUser)
        accountRepository.findByUserId(id).ifPresent { currentAccount ->
            val updatedAccount = currentAccount.copy(
                firstName = request.firstName ?: currentAccount.firstName,
                lastName = request.lastName ?: currentAccount.lastName,
                cep = request.cep ?: currentAccount.cep
            )
            accountRepository.save(updatedAccount)
        }
        return userMapper.toDTO(savedUser)
    }

    @Transactional
    fun deleteUser(id: Long) {
        accountRepository.findByUserId(id).ifPresent { account ->
            accountRepository.delete(account)
        }
        userRepository.deleteById(id)
    }
}