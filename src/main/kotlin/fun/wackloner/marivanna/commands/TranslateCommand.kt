package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.AppContext
import `fun`.wackloner.marivanna.Bot
import `fun`.wackloner.marivanna.Emoji
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


// TODO: promts to separate file
fun promptTranslate(userId: Int, chatId: Long) {
    AppContext.bot.sendUpdate(chatId,
            "Hey, sexy, I'll translate everything for you${Emoji.WINKING}\n\n<i>Enter a word/phrase:</i>", menuKeyboard())
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
