package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.handlers.processTranslate
import `fun`.wackloner.marivanna.model.Emoji
import `fun`.wackloner.marivanna.utils.menuKeyboard
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


// TODO: promts to separate file
fun promptTranslate(chatId: Long) {
    Context.bot.sendUpdate(chatId,
            "Hey, sexy, I'll translate everything for you${Emoji.WINKING}\n\n<i>Enter a word/phrase:</i>", menuKeyboard())
    Context.waitingForTranslate = true
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
