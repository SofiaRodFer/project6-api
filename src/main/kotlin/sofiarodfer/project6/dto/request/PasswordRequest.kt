package sofiarodfer.project6.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class PasswordRequest(
    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(max = 255, message = "Password must be less than 256 characters long")
    val password: String
)
