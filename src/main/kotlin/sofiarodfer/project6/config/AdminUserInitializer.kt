package sofiarodfer.project6.config

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import sofiarodfer.project6.config.properties.*
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
    private val securityProperties: SecurityProperties,
    private val initializationProperties: InitializationProperties
) : ApplicationRunner {

    private val logger = LoggerFactory.getLogger(AdminUserInitializer::class.java)

    override fun run(args: ApplicationArguments?) {
        val adminRoleName = securityProperties.findAdminRole()
        val adminAppUser = initializationProperties.getAdminAppUser()
        val adminRole = roleRepository.findByName(adminRoleName).orElseGet {
            logger.info("Role '$adminRoleName' não encontrada, criando...")
            roleRepository.save(Role(name = adminRoleName))
        }
        if (userRepository.findByUsername(adminAppUser.username).isEmpty) {
            logger.info("Usuário admin de produção '${adminAppUser.username}' não encontrado, criando...")
            val adminUser = User(
                username = adminAppUser.username,
                password = passwordEncoder.encode(adminAppUser.password),
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
            logger.info("Usuário admin de produção '${adminAppUser.username}' criado com sucesso.")
        } else {
            logger.info("Usuário admin de produção '${adminAppUser.username}' já existe. Nenhuma ação necessária.")
        }
    }
}