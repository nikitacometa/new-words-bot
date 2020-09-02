package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.utils.mainMenuKeyboard
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


@Component
class StartCommand : KoreshCommand("start", "greet") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        try {
            bot.sendText(
                    message.chatId,
                    "Guys who learn languages are turning me on${Emojis.WINKING}${Emojis.WINKING}",
                    mainMenuKeyboard(message.chatId)
            )
        } catch (exception: TelegramApiException) {
            logger.error { exception }
            // TODO: retry (make a method-wrapper for it)
        }
    }

}