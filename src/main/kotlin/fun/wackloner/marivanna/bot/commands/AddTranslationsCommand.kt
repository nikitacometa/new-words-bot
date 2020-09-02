package `fun`.wackloner.marivanna.bot.commands


import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.handlers.processAddTranslations
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


@Component
class AddTranslationsCommand : KoreshCommand("add_translations", "add new translations") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val wholeText = arguments.joinToString(" ")

        try {
            processAddTranslations(wholeText, message.from.id, message.chatId)
        } catch (e: Exception) {
            logger.error { e }
        }
    }
}
