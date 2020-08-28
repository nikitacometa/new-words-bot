package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.handlers.processTranslate
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


@Component
class TranslateCommand : KoreshCommand("translate", "translate a word or a phrase and save translations") {

    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val wholeText = arguments.joinToString(" ")

        try {
            processTranslate(wholeText, message.chatId)
        } catch (e: Exception) {
            logger.error { e }
        }
    }

}
