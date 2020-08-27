package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.model.Emoji
import `fun`.wackloner.marivanna.utils.mainMenuKeyboard
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

// TODO: add settings button

@Component
class StartCommand : KoreshCommand("start", "greet") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        try {
            val reply = bot.sendText(
                    message.chatId,
                    "Guys who learn languages are turning me on${Emoji.WINKING}${Emoji.WINKING}",
                    mainMenuKeyboard()
            )
            Context.actionMessageId = reply.messageId
        } catch (exception: TelegramApiException) {
            logger.error { exception }
            // TODO: retry (make a method-wrapper for it)
        }
    }

}