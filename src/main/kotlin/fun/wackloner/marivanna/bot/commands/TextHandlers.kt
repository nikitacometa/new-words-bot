package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import org.telegram.telegrambots.meta.api.objects.Message

fun tryProcessCommandData(message: Message): Boolean {
    if (Context.waitingForTranslation) {
        processNewTranslation(message.text, message.from.id, message.chatId)
        Context.waitingForTranslation = false
        return true
    }

    if (Context.waitingForTranslate) {
        processTranslate(message.text, message.chatId)
        Context.waitingForTranslate = false
        return true
    }

    return false
}

fun processTranslate(text: String, chatId: Long) {
    val translation = Context.translationService.translate(text, Settings.NATIVE_LANGUAGE)
    Context.lastTranslation = translation
    // TODO: emoji before text
    Context.bot.sendUpdate(chatId, "<b>${formatSingleTranslation(text, translation.translated)}</b>", saveKeyboard())
}
