package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.utils.dictionaryOrMenu
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import `fun`.wackloner.marivanna.utils.mainMenuKeyboard
import `fun`.wackloner.marivanna.utils.saveKeyboard

fun processSave(userId: Int, chatId: Long) {
    val translation = Context.forChat(chatId).lastTranslation
    if (translation == null) {
        Context.bot.sendUpdate(chatId, "There was not translations yet... Translate something?)", mainMenuKeyboard(chatId))
        return
    }

    try {
        Context.expressionManager.addTranslation(userId, chatId, translation)
    } catch (e: Exception) {
        Context.bot.sendUpdate(chatId,
                "Sorry, sweetheart, I failed to save '${translation.translation}'. Should the bad girl try again?..", saveKeyboard())
        return
    }

    val resultStr = formatSingleTranslation(translation.expression, translation.translation)
    Context.bot.sendUpdate(chatId,
            "$resultStr [saved]\n\n<i>Enter a word/phrase to translate more:</i>", dictionaryOrMenu())
}
