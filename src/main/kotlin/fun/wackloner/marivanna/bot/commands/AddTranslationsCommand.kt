package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.model.Emoji
import `fun`.wackloner.marivanna.utils.afterAddKeyboard
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import `fun`.wackloner.marivanna.utils.retryKeyboard

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


fun processAddTranslations(text: String, userId: Int, chatId: Long) {
    val newTranslations = Context.expressionManager.addTranslationsFromString(text, userId)
    if (newTranslations.isEmpty()) {
        Context.bot.sendUpdate(chatId, "Sorry, my love, I failed to add the translation... Try again?", retryKeyboard())
        return
    }

    val translationsStr = newTranslations.joinToString("\n") { formatSingleTranslation(it.expression, it.translation) }
    Context.bot.sendUpdate(chatId,
            "Wow, you're so smart${Emoji.WINKING}\n\n<i>New translations:</i>\n$translationsStr", afterAddKeyboard())
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
