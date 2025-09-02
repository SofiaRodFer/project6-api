package sofiarodfer.project6.controller

import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import sofiarodfer.project6.dto.request.AuthRequest
import sofiarodfer.project6.dto.response.AuthResponse
import sofiarodfer.project6.service.JwtService

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authManager: AuthenticationManager,
    private val jwtService: JwtService
) {
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: AuthRequest): AuthResponse {
        val authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        val user = authentication.principal as org.springframework.security.core.userdetails.User
        val roles = user.authorities.map { it.authority }
        val token = jwtService.generateToken(user.username, roles)
        return AuthResponse(token)
    }
}