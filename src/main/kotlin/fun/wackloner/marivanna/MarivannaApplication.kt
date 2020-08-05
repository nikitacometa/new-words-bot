package `fun`.wackloner.marivanna

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry


val logger = KotlinLogging.logger {}

@SpringBootApplication
class MarivannaApplication : SpringBootServletInitializer() {
	@Bean
	fun getCommandRegistry(): CommandRegistry = CommandRegistry(true, Settings.BOT_USERNAME)

}

fun main(args: Array<String>) {
	val context = runApplication<MarivannaApplication>(*args)

	AppContext.ctx = context

	val environment = context.getBean(Environment::class.java)

	logger.info { "Available at http://localhost:${environment.getProperty("local.server.port")?.toInt()}" }
}
