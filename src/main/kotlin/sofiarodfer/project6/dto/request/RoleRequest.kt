package sofiarodfer.project6.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RoleRequest(
    @field:NotBlank(message = "Role name cannot be blank")
    @field:Size(min = 1, max = 50, message = "Role name must be between 1 and 50 characters")
    val name: String
)