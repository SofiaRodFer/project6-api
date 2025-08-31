package sofiarodfer.project6.mapper

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import sofiarodfer.project6.dto.UserDTO
import sofiarodfer.project6.dto.request.UserCreateRequest
import sofiarodfer.project6.dto.response.UserResponse
import sofiarodfer.project6.entity.Account
import sofiarodfer.project6.entity.Role
import sofiarodfer.project6.entity.User

@Component
class UserMapper(private val passwordEncoder: PasswordEncoder) {

    fun toEntity(request: UserCreateRequest, roles: Set<Role>): User {
        return User(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            roles = roles
        )
    }

    fun toDTO(user: User): UserDTO {
        return UserDTO(
            id = user.id,
            username = user.username,
            enabled = user.enabled,
            roles = user.roles.map { it.name }.toSet()
        )
    }

    fun toResponse(dto: UserDTO, account: Account?): UserResponse {
        return UserResponse(
            id = dto.id,
            username = dto.username,
            enabled = dto.enabled,
            roles = dto.roles,
            account = account?.let {
                UserResponse.AccountInfo(
                    firstName = it.firstName,
                    lastName = it.lastName,
                    cep = it.cep
                )
            }
        )
    }
}