package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.AppContext
import `fun`.wackloner.marivanna.Settings
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import org.telegram.telegrambots.meta.api.objects.Message

fun tryProcessCommandData(message: Message): Boolean {
    if (AppContext.waitingForTranslation) {
        processNewTranslation(message.text, message.from.id, message.chatId)
        AppContext.waitingForTranslation = false
        return true
    }

    if (AppContext.waitingForTranslate) {
        processTranslate(message.text, message.chatId)
        AppContext.waitingForTranslate = false
        return true
    }

    return false
}

fun processTranslate(text: String, chatId: Long) {
    val translation = AppContext.translationService.translate(text, Settings.NATIVE_LANGUAGE)
    AppContext.lastTranslation = translation
    // TODO: emoji before text
    AppContext.bot.sendUpdate(chatId, "<b>${formatSingleTranslation(text, translation.translated)}</b>", saveKeyboard())
}
