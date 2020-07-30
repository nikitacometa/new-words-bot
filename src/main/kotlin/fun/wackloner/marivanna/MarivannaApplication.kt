package `fun`.wackloner.marivanna

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
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

	val applicationContext: ApplicationContext = runApplication<MarivannaApplication>(*args)
	applicationContext.beanDefinitionNames.forEach { println(it) }

// TODO: find out why doesn't the following code compile (or is it just IDEA?)
//	val bot = applicationContext.getBean(Bot.class)
}
