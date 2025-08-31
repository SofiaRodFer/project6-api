package sofiarodfer.project6.dto

data class UserDTO(
    val id: Long,
    val username: String,
    val enabled: Boolean,
    val roles: Set<String>,
    val account: AccountDTO?
) {
    data class AccountDTO(
        val firstName: String,
        val lastName: String,
        val cep: String
    )
}