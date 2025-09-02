package sofiarodfer.project6.config

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import sofiarodfer.project6.entity.Role
import sofiarodfer.project6.repository.RoleRepository

@Component
class RoleInitializer(
    private val roleRepository: RoleRepository
): ApplicationRunner {

    private val logger = LoggerFactory.getLogger(RoleInitializer::class.java)

    override fun run(args: ApplicationArguments?) {
        val normalRole = roleRepository.findByName("NONE").orElseGet {
            logger.info("Role NONE não encontrada, criando...")
            roleRepository.save(Role(name = "NONE"))
        }
    }
}