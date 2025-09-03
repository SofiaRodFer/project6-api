package sofiarodfer.project6.dto.response

import sofiarodfer.project6.entity.Role

data class UserResponse(
    val id: Long,
    val username: String,
    val enabled: Boolean,
    val roles: Set<Role>,
    val account: AccountInfo?
) {
    data class AccountInfo(
        val firstName: String,
        val lastName: String,
        val cep: String
    )
}