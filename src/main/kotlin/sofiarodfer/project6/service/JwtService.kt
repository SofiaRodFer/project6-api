package sofiarodfer.project6.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import sofiarodfer.project6.config.properties.JwtProperties
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(
    private val jwtProperties: JwtProperties
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun generateToken(username: String, roles: Collection<String>): String =
        Jwts.builder()
            .setSubject(username)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.expiration))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

    fun extractUsername(token: String): String? =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    fun extractRoles(token: String): List<String> =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .get("roles", List::class.java)
            .map { it.toString() }
}