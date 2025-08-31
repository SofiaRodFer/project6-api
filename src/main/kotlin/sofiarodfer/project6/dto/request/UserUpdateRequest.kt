package sofiarodfer.project6.dto.request

data class UserUpdateRequest(
    val roles: Set<String>?,
    val enabled: Boolean?,
    val firstName: String?,
    val lastName: String?,
    val cep: String?
)