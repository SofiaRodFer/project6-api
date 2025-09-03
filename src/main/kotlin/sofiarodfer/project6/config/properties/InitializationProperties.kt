package sofiarodfer.project6.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import sofiarodfer.project6.enum.RoleEnum

@ConfigurationProperties("app.initialization")
data class InitializationProperties(
    val users: List<AppUser>
)

data class AppUser(
    val identifier: RoleEnum,
    val username: String,
    val password: String
)

fun InitializationProperties.getAdminAppUser(): AppUser {
    return this.users.find { it.identifier == RoleEnum.ADMIN } ?: throw RuntimeException("Admin user not found")
}