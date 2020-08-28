package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.model.Emoji
import `fun`.wackloner.marivanna.utils.afterAddKeyboard
import `fun`.wackloner.marivanna.utils.retryKeyboard

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


fun processAddTranslations(text: String, userId: Int, chatId: Long) {
    val newTranslation = Context.translationManager.addTranslationsFromString(text, userId)
    if (newTranslation == null) {
        Context.bot.sendUpdate(chatId, "I failed to add <i>'$text'</i>... Try again?", retryKeyboard())
        return
    }

    Context.bot.sendUpdate(chatId,
            "Wow, you're so smart${Emoji.WINKING}\n\n${newTranslation.beautifulHtml()}", afterAddKeyboard())
}

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
