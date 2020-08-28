package `fun`.wackloner.marivanna.bot.handlers

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.bot.commands.processAddTranslations
import `fun`.wackloner.marivanna.utils.saveKeyboard
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import org.telegram.telegrambots.meta.api.objects.Message

fun tryProcessCommandData(message: Message): Boolean {
    if (Context.waitingForTranslation) {
        processAddTranslations(message.text, message.from.id, message.chatId)
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
    if (text.length > Settings.MAX_TEXT_LENGTH) {
        Context.bot.sendText(chatId, "WOW, your text is SO big! (too big actually, try <${Settings.MAX_TEXT_LENGTH} symbols)")
        return
    }

    val translation = Context.translationService.translate(text, Settings.NATIVE_LANGUAGE)
    Context.lastTranslation = translation
    Context.bot.sendUpdate(chatId, formatSingleTranslation(text, translation.translation), saveKeyboard())
}
