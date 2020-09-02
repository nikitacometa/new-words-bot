package `fun`.wackloner.marivanna.bot.handlers

import `fun`.wackloner.marivanna.bot.*
import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.bot.commands.*
import `fun`.wackloner.marivanna.model.Operations
import `fun`.wackloner.marivanna.utils.*
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

fun processMenu(chatId: Long) {
    resetInputRequests()

    Context.bot.sendUpdate(chatId, "I'll do whatever you want${Emojis.LOVE_FACE}", mainMenuKeyboard())
}

fun processSave(userId: Int, chatId: Long) {
    val translation = Context.lastTranslation
    if (translation == null) {
        Context.bot.sendUpdate(chatId, "There was not translations yet... Translate something?)", mainMenuKeyboard())
        return
    }

    try {
        Context.expressionManager.addTranslation(userId, translation)
    } catch (e: Exception) {
        Context.bot.sendUpdate(chatId,
                "Sorry, sweetheart, I failed to save '${translation.translation}'. Should the bad girl try again?..", saveKeyboard())
        return
    }

    val resultStr = formatSingleTranslation(translation.expression, translation.translation)
    Context.bot.sendUpdate(chatId,
            "$resultStr [saved]\n\n<i>Enter a word/phrase to translate more:</i>", dictionaryOrMenu())
}

fun processSettings(chatId: Long) {
    resetInputRequests()
    sendInProgress(chatId)
}

fun sendInProgress(chatId: Long) =
        Context.bot.sendUpdate(chatId, "${Emojis.SPIRAL} <i><b>4:20</b></i> ${Emojis.SPIRAL}",
                oneLineKeyboard(newButton("Menu", Operations.MENU)))


fun processCallbackQuery(callbackQuery: CallbackQuery) {
    val text = callbackQuery.data
    val userId = callbackQuery.from.id
    val chatId: Long = userId.toLong()

    when (text) {
        Operations.DICTIONARY -> showDictionary(userId, chatId)
        Operations.ADD_TRANSLATION -> promptTranslation(chatId)
        Operations.MENU -> processMenu(chatId)
        Operations.TRANSLATE -> promptTranslate(chatId)
        Operations.SAVE -> processSave(userId, chatId)
        Operations.SETTINGS -> processSettings(chatId)
        else -> sendInProgress(chatId)
    }
}
