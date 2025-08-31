package sofiarodfer.project6.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.dto.response.MeResponse
import sofiarodfer.project6.repository.AccountRepository
import sofiarodfer.project6.repository.UserRepository

@RestController
@RequestMapping("/users")
class UserController(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository
) {
    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal userDetails: UserDetails): MeResponse {
        val user = userRepository.findByUsername(userDetails.username)
            .orElseThrow { RuntimeException("User not found") }
        val account = accountRepository.findByUserId(user.id)
            .orElseThrow { RuntimeException("Account not found") }

        return MeResponse(
            username = user.username,
            roles = user.roles.map { it.name },
            firstName = account?.firstName,
            lastName = account?.lastName,
            cep = account?.cep
        )
    }
}