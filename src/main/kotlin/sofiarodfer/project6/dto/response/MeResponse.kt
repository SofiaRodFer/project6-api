package sofiarodfer.project6.dto.response

data class MeResponse(
    val username: String,
    val roles: List<String>,
    val firstName: String?,
    val lastName: String?,
    val cep: String?
)
