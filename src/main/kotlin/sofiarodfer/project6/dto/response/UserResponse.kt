package sofiarodfer.project6.dto.response

data class UserResponse(
    val id: Long,
    val username: String,
    val enabled: Boolean,
    val roles: Set<String>,
    val account: AccountInfo?
) {
    data class AccountInfo(
        val firstName: String,
        val lastName: String,
        val cep: String
    )
}