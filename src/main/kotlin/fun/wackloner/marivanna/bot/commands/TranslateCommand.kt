package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import `fun`.wackloner.marivanna.utils.saveKeyboard
import `fun`.wackloner.marivanna.utils.translateKeyboard
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


fun promptTranslate(chatId: Long) {
    // TODO: add button to switch languages
    Context.bot.sendUpdate(chatId, "<i>Enter a word/phrase:</i>", translateKeyboard(chatId))
    Context.forChat(chatId).waitingForTranslate = true
}

fun processTranslate(text: String, chatId: Long) {
    if (text.length > Settings.MAX_TEXT_LENGTH) {
        Context.bot.sendText(chatId, "WOW, your text is SO big! (too big actually, try <${Settings.MAX_TEXT_LENGTH} symbols)")
        return
    }

    try {
        val translation = Context.translationService.translate(text, Context.forChat(chatId).destLanguage)
        Context.forChat(chatId).lastTranslation = translation
        // TODO: refactor
        if (Context.forChat(chatId).languagesSwapped) {
            Context.forChat(chatId).lastTranslation = translation.swapped()
        }
        Context.bot.sendUpdate(chatId, "${formatSingleTranslation(text, translation.translation)}\n\n" +
                "<i>Enter a word/phrase to translate more:</i>", saveKeyboard(chatId))
    } catch (e: Exception) {
        Bot.logger.error { e }
        Context.bot.sendUpdate(chatId,
                "Failed, translator sucks... Try again?\n\n<i>Enter a word/phrase:</i>", translateKeyboard(chatId))
    }
}


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
