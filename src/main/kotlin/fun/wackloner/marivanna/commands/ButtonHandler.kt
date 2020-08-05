package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.AppContext
import `fun`.wackloner.marivanna.Emoji
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message

fun processCallbackQuery(callbackQuery: CallbackQuery) {
    val text = callbackQuery.data
    val userId = callbackQuery.from.id
    val chatId: Long = userId.toLong()

    when (text) {
        "dictionary" -> showDictionary(userId, chatId)
        "addTranslation" -> promptTranslation(userId, chatId)
        "cancel" -> AppContext.bot.sendUpdate(chatId, "Let's try again sweetie${Emoji.LOVE_FACE}", mainMenuKeyboard())
    }
}

fun tryProcessCommandData(message: Message): Boolean {
    if (AppContext.waitingForTranslation) {
        processNewTranslation(message.text, message.from.id, message.chatId)

        AppContext.waitingForTranslation = false

        return true
    }

    return false
}