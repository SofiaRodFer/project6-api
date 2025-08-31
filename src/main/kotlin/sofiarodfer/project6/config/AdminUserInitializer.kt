package sofiarodfer.project6.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import sofiarodfer.project6.entity.Account
import sofiarodfer.project6.entity.Role
import sofiarodfer.project6.entity.User
import sofiarodfer.project6.repository.AccountRepository
import sofiarodfer.project6.repository.RoleRepository
import sofiarodfer.project6.repository.UserRepository

@Component
class AdminUserInitializer(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${initial.admin.username}") private val adminUsername: String,
    @Value("\${initial.admin.password}") private val adminPassword: String
) : ApplicationRunner {

    private val logger = LoggerFactory.getLogger(AdminUserInitializer::class.java)

    override fun run(args: ApplicationArguments?) {
        val adminRole = roleRepository.findByName("ADMIN").orElseGet {
            logger.info("Role ADMIN não encontrada, criando...")
            roleRepository.save(Role(name = "ADMIN"))
        }
        if (userRepository.findByUsername(adminUsername).isEmpty) {
            logger.info("Usuário admin de produção '$adminUsername' não encontrado, criando...")
            val adminUser = User(
                username = adminUsername,
                password = passwordEncoder.encode(adminPassword),
                roles = setOf(adminRole),
                enabled = true
            )
            val savedUser = userRepository.save(adminUser)
            val adminAccount = Account(
                firstName = "Admin",
                lastName = "System",
                cep = "00000000",
                user = savedUser
            )
            accountRepository.save(adminAccount)
            logger.info("Usuário admin de produção '$adminUsername' criado com sucesso.")
        } else {
            logger.info("Usuário admin de produção '$adminUsername' já existe. Nenhuma ação necessária.")
        }
    }
}