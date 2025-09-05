package sofiarodfer.project6.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import sofiarodfer.project6.entity.User
import sofiarodfer.project6.enum.RoleEnum

@ConfigurationProperties("app.security")
data class SecurityProperties(
    val roles: List<RoleProperties>
)

data class RoleProperties(
    val identifier: RoleEnum,
    val name: String
)

fun SecurityProperties.findRoleByIdentifier(identifier: RoleEnum) =
    this.roles.find { it.identifier == identifier }?.name
        ?: throw RuntimeException("$identifier role not found")

fun SecurityProperties.findAdminRole() =
    findRoleByIdentifier(RoleEnum.ADMIN)

fun SecurityProperties.isRoleDeletable(roleName: String) =
    this.roles.find { it.name == roleName } == null


fun SecurityProperties.isUserAdmin(user: User) =
    user.roles.find { it.name == findAdminRole() } != null
