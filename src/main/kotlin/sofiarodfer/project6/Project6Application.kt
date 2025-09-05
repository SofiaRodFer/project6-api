package sofiarodfer.project6

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import sofiarodfer.project6.config.properties.InitializationProperties
import sofiarodfer.project6.config.properties.JwtProperties
import sofiarodfer.project6.config.properties.SecurityProperties

@SpringBootApplication
@EnableConfigurationProperties(
	JwtProperties::class,
	SecurityProperties::class,
	InitializationProperties::class
)
class Project6Application

fun main(args: Array<String>) {
	runApplication<Project6Application>(*args)
}
