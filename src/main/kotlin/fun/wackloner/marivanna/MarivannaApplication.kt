package `fun`.wackloner.marivanna

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


@SpringBootApplication
class MarivannaApplication

fun main(args: Array<String>) {
	ApiContextInitializer.init()

	val botsApi = TelegramBotsApi()
	val bot = Bot()

	try {
		botsApi.registerBot(bot)
	} catch (e: TelegramApiException) {
		e.printStackTrace()
		return
	}

	runApplication<MarivannaApplication>(*args)
}
