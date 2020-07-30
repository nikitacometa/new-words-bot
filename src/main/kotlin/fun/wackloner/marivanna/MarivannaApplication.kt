package `fun`.wackloner.marivanna

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry


@SpringBootApplication
class MarivannaApplication {
	@Bean
	fun getCommandRegistry(): CommandRegistry = CommandRegistry(true, Settings.BOT_USERNAME)

}

fun main(args: Array<String>) {
	ApiContextInitializer.init()

	runApplication<MarivannaApplication>(*args)
}
