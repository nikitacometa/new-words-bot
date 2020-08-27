package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.Emoji
import `fun`.wackloner.marivanna.model.Phrase
import `fun`.wackloner.marivanna.model.UserTranslation

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

/**
 * TODO: implement TranslationManager
 */

fun learning(text: String): Phrase = Phrase(text, Settings.LEARNING_LANGUAGE)

fun native(vararg texts: String): Map<String, List<String>> =
        mutableMapOf(Pair(Settings.NATIVE_LANGUAGE, texts.toList().map { it.trim() }))

data class SplitResult(val phrase: String?, val options: String?) {
    companion object {
        val NULL = SplitResult(null, null)
    }
}

fun splitTranslationText(text: String): SplitResult {
    // TODO: refactor
    val delimiter = when {
        text.contains("—") -> "—"
        text.contains(" - ") -> " - "
        else -> null
    }
    if (delimiter.isNullOrBlank())
        return SplitResult.NULL

    val (phrase, options) = text.split(delimiter)

    // TODO: handle prettier
    return SplitResult(phrase, options)
}

fun addTranslation(text: String, userId: Int): UserTranslation? {
    val (phrase, options) = splitTranslationText(text)
    if (phrase.isNullOrBlank() || options.isNullOrBlank())
        return null

    // TODO: extract language or verify within settings
    val newTranslations = native(*options.split(";").toTypedArray())


    // TODO: update if exists
    // TODO: filter duplicates
    return Context.translationRepository.save(UserTranslation(userId, phrase, Settings.NATIVE_LANGUAGE, newTranslations))
}

fun processNewTranslation(text: String, userId: Int, chatId: Long) {
    val newTranslation = addTranslation(text, userId)
    if (newTranslation == null) {
        // TODO: 'try again?'
        Context.bot.sendUpdate(chatId, "I failed to add <i>'$text</i>...", mainMenuKeyboard())
        return
    }

    Context.bot.sendUpdate(chatId,
            "Wow, you're so smart${Emoji.WINKING}\n\n${newTranslation.beautifulHtml()}", mainMenuKeyboard())
}

fun promptTranslation(chatId: Long) {
    Context.bot.sendUpdate(
            chatId,
            "Send me a new translation, Darling ${Emoji.SEND_KISS}\nFor example:\n\n<i>This bot is sooo cool — этот бот таак хорош; это бот быть тааак круто</i>",
            menuKeyboard()
    )
    Context.waitingForTranslation = true
}

@Component
class AddTranslationCommand : KoreshCommand("add_translation", "add a new translation") {

    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val wholeText = arguments.joinToString(" ")

        try {
            processNewTranslation(wholeText, message.from.id, message.chatId)
        } catch (e: Exception) {
            logger.error { e }
        }
    }

}
