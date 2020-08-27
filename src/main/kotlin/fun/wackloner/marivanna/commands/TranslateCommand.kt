package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.AppContext
import `fun`.wackloner.marivanna.Bot
import `fun`.wackloner.marivanna.Settings
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


fun processTranslate(text: String, chatId: Long) {
    val translation = AppContext.translationService.translate(text, Settings.NATIVE_LANGUAGE)
    AppContext.lastTranslation = translation
    AppContext.bot.sendUpdate(chatId, "<b>$text:</b>", translateKeyboard(translation))
}

fun promptTranslate(userId: Int, chatId: Long) {
    AppContext.bot.sendUpdate(chatId, "Hey, sexy boy, what to translate? ;)", cancelKeyboard())
    AppContext.waitingForTranslate = true
}

@Component
class TranslateCommand : KoreshCommand("translate", "translate a phrase and possibly save translations") {

    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val wholeText = arguments.joinToString(" ")

        try {
            processTranslate(wholeText, message.chatId)
        } catch (e: Exception) {
            logger.error { e }
        }
    }

}
