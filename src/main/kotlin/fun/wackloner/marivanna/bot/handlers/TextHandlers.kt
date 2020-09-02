package `fun`.wackloner.marivanna.bot.handlers

import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.utils.*
import org.telegram.telegrambots.meta.api.objects.Message

fun tryProcessCommandData(message: Message): Boolean {
    if (Context.waitingForTranslation) {
        processAddTranslations(message.text.toLowerCase(), message.from.id, message.chatId)
        return true
    }

    // TODO: maybe allow storing camel case as well (?)
    if (Context.waitingForTranslate) {
        processTranslate(message.text.toLowerCase(), message.chatId)
        return true
    }

    return false
}

fun processAddTranslations(text: String, userId: Int, chatId: Long) {
    val newTranslations = Context.expressionManager.addTranslationsFromString(text, userId)
    if (newTranslations.isEmpty()) {
        Context.bot.sendUpdate(chatId, "Sorry, my love, I failed to add the translation... Try again?", dictionaryOrMenu())
        return
    }

    val translationsStr = newTranslations.joinToString("\n") { formatSingleTranslation(it.expression, it.translation) }
    Context.bot.sendUpdate(chatId, "Wow, you're so smart${Emojis.WINKING}\n\n<i>New translations:</i>\n" +
            "$translationsStr\n\n<i>Enter more translations:</i>", dictionaryOrMenu())
}

fun processTranslate(text: String, chatId: Long) {
    if (text.length > Settings.MAX_TEXT_LENGTH) {
        Context.bot.sendText(chatId, "WOW, your text is SO big! (too big actually, try <${Settings.MAX_TEXT_LENGTH} symbols)")
        return
    }

    try {
        val translation = Context.translationService.translate(text, Settings.NATIVE_LANGUAGE)
        Context.lastTranslation = translation
        Context.bot.sendUpdate(chatId, "${formatSingleTranslation(text, translation.translation)}\n\n" +
                "<i>Enter a word/phrase to translate more:</i>", saveKeyboard())
    } catch (e: Exception) {
        Bot.logger.error { e }
        Context.bot.sendUpdate(chatId,
                "Failed, translator sucks... Try again?\n\n<i>Enter a word/phrase:</i>", dictionaryOrMenu())
    }
}
