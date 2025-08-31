package sofiarodfer.project6.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import sofiarodfer.project6.repository.UserRepository

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found") }

        val authorities = user.roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }

        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            user.enabled,
            true, true, true,
            authorities
        )
    }
}