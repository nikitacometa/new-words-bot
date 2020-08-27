package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.AppContext
import `fun`.wackloner.marivanna.Emoji
import `fun`.wackloner.marivanna.Settings
import `fun`.wackloner.marivanna.UserTranslation
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message

fun processCancel(text: String, chatId: Long) {
    if (AppContext.waitingForTranslation) {
        AppContext.waitingForTranslation = false
    }
    if (AppContext.waitingForTranslate) {
        AppContext.waitingForTranslate = false
    }

    AppContext.bot.sendUpdate(chatId, "Let's try again sweetie${Emoji.LOVE_FACE}", mainMenuKeyboard())
}

fun processCallbackQuery(callbackQuery: CallbackQuery) {
    val text = callbackQuery.data
    val userId = callbackQuery.from.id
    val chatId: Long = userId.toLong()

    when (text) {
        "?dictionary" -> showDictionary(userId, chatId)
        "?addTranslation" -> promptTranslation(userId, chatId)
        "?cancel" -> processCancel(text, chatId)
        "?translate" -> promptTranslate(userId, chatId)
    }

    val num = text.toIntOrNull()
    if (num != null) {
        // save translation request
        // TODO: handle when there was no translation yet
        val translation = AppContext.lastTranslation
        if (translation == null) {
            AppContext.bot.sendUpdate(chatId, "There was not translations yet...", mainMenuKeyboard())
            return
        }

        val saved = AppContext.translationRepository.save(UserTranslation(userId, translation.phrase,
                Settings.NATIVE_LANGUAGE, mapOf(translation.destLang to translation.translations)))
        AppContext.bot.sendUpdate(chatId, "$saved", mainMenuKeyboard())
    }
}

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
