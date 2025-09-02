package sofiarodfer.project6.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserCreateRequest(
    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(max = 255, message = "Password must be less than 256 characters long")
    val password: String,

    val roles: Set<String>? = setOf("NONE"),

    @field:NotBlank(message = "First name cannot be blank")
    @field:Size(max = 100, message = "First name cannot be longer than 100 characters")
    val firstName: String,

    @field:NotBlank(message = "Last name cannot be blank")
    @field:Size(max = 100, message = "Last name cannot be longer than 100 characters")
    val lastName: String,

    @field:NotBlank
    @field:Size(min = 8, max = 8, message = "CEP must have 8 characters")
    val cep: String
)