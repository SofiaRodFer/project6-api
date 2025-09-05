package sofiarodfer.project6.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import sofiarodfer.project6.entity.User
import sofiarodfer.project6.enum.RoleEnum

@ConfigurationProperties("app.security")
data class SecurityProperties(
    val roles: List<Role>
)

data class Role(
    val identifier: RoleEnum,
    val name: String
)

fun SecurityProperties.findRoleByIdentifier(identifier: RoleEnum): String {
    val role = this.roles.find { it.identifier == identifier }
    return role?.name ?: throw RuntimeException("$identifier role not found")
}

fun SecurityProperties.findAdminRole(): String {
    return findRoleByIdentifier(RoleEnum.ADMIN)
}

fun SecurityProperties.isRoleDeletable(roleName: String): Boolean {
    val role = this.roles.find { it.name == roleName }
    return role == null
}

fun SecurityProperties.isUserAdmin(user: User): Boolean {
    val adminRole = user.roles.find { it.name == findAdminRole() }
    return adminRole != null
}