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
        val adminAppUser = initializationProperties.getAdminAppUser()
        val adminRole = retrieveAdminRole()
        if (userRepository.findByUsername(adminAppUser.username).isEmpty) {
            logger.info("Production admin user '${adminAppUser.username}' not found, creating...")
            val savedUser = createAdminUser(adminAppUser, adminRole)
            createAdminAccount(savedUser)
            logger.info("Production admin user '${adminAppUser.username}' successfully created.")
        } else {
            logger.info("Production admin user '${adminAppUser.username}' already exists. No action necessary.")
        }
    }

    private fun retrieveAdminRole(): Role {
        val adminRoleName = securityProperties.findAdminRole()
        return roleRepository.findByName(adminRoleName).orElseGet {
            logger.info("Role '$adminRoleName' not found, creating...")
            roleRepository.save(Role(name = adminRoleName))
        }
    }

    private fun createAdminUser(adminAppUser: AppUser, adminRole: Role): User {
        val adminUser = User(
            username = adminAppUser.username,
            password = passwordEncoder.encode(adminAppUser.password),
            roles = setOf(adminRole),
            enabled = true
        )
        return userRepository.save(adminUser)
    }

    private fun createAdminAccount(savedUser: User): Account {
        val adminAccount = Account(
            firstName = "Admin",
            lastName = "System",
            cep = "00000000",
            user = savedUser
        )
        return accountRepository.save(adminAccount)
    }

}