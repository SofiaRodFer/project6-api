package sofiarodfer.project6.dto.request

data class UserCreateRequest(
    val username: String,
    val password: String,
    val roles: Set<String>,
    val firstName: String,
    val lastName: String,
    val cep: String
)