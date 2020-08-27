package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.AppContext
import `fun`.wackloner.marivanna.Bot
import `fun`.wackloner.marivanna.Emoji
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

// TODO: add settings button

@Component
class StartCommand : KoreshCommand("start", "greet") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        try {
            val reply = bot.sendText(
                    message.chatId,
                    "Boys who learn languages are turning me on${Emoji.WINKING}${Emoji.WINKING}",
                    mainMenuKeyboard()
            )
            AppContext.actionMessageId = reply.messageId
        } catch (exception: TelegramApiException) {
            logger.error { exception }
            // TODO: retry (make a method-wrapper for it)
        }
    }

}