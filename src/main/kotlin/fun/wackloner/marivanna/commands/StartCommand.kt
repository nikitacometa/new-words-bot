package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.Bot
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

fun newButton(text: String, callbackData: String): InlineKeyboardButton = InlineKeyboardButton(text).setCallbackData(callbackData)

@Component
class StartCommand : KoreshCommand("start", "greet") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val keyboardMarkup = InlineKeyboardMarkup()

        keyboardMarkup.keyboard = listOf(
                listOf(
                        newButton("Translate", "translate"),
                        newButton("Add translation", "addTranslation")
                ),
                listOf(
                        newButton("Quiz", "quiz"),
                        newButton("Schedule tests", "schedule")
                ),
                listOf(
                        newButton("Show dictionary", "dictionary")
                )
        )

        try {
            val reply = bot.sendText(message.chatId, "Learning languages is fucking useful.", keyboardMarkup)
        } catch (exception: TelegramApiException) {
            logger.error { exception }
            // TODO: retry (make a method-wrapper for it)
        }
    }

}