package sofiarodfer.project6.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthRequest(
    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(max = 255, message = "Password must be less than 256 characters long")
    val password: String
)