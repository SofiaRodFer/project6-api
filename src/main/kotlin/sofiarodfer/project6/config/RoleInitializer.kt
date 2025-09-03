package sofiarodfer.project6.config

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import sofiarodfer.project6.config.properties.SecurityProperties
import sofiarodfer.project6.config.properties.findRoleByIdentifier
import sofiarodfer.project6.entity.Role
import sofiarodfer.project6.enum.RoleEnum
import sofiarodfer.project6.repository.RoleRepository

@Component
class RoleInitializer(
    private val roleRepository: RoleRepository,
    private val securityProperties: SecurityProperties
): ApplicationRunner {

    private val logger = LoggerFactory.getLogger(RoleInitializer::class.java)

    override fun run(args: ApplicationArguments?) {
        val defaultRoleName = securityProperties.findRoleByIdentifier(RoleEnum.DEFAULT)
        roleRepository.findByName(defaultRoleName).orElseGet {
            logger.info("Role '$defaultRoleName' não encontrada, criando...")
            roleRepository.save(Role(name = defaultRoleName))
        }
    }
}