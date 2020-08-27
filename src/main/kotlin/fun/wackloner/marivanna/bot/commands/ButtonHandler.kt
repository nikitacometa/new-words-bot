package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.model.Emoji
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.UserTranslation
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

fun processMenu(chatId: Long) {
    // TODO: also set them false on other commands
    if (Context.waitingForTranslation) {
        Context.waitingForTranslation = false
    }
    if (Context.waitingForTranslate) {
        Context.waitingForTranslate = false
    }

    Context.bot.sendUpdate(chatId, "I will do whatever you want${Emoji.LOVE_FACE} (not really)", mainMenuKeyboard())
}

fun processSave(userId: Int, chatId: Long) {
    val translation = Context.lastTranslation
    if (translation == null) {
        Context.bot.sendUpdate(chatId, "There was not translations yet... Translate something?)", mainMenuKeyboard())
        return
    }

    try {
        Context.translationRepository.save(UserTranslation(userId, translation.phrase,
                Settings.NATIVE_LANGUAGE, mapOf(translation.destLang to listOf(translation.translated))))
    } catch (e: Exception) {
        Context.bot.sendUpdate(chatId,
                "Sorry, sweetheart, I failed to save '${translation.translated}'. Should the bad girl try again?..", saveKeyboard())
        return
    }

    val resultStr = formatSingleTranslation(translation.phrase, translation.translated)
    // TODO: 'oh DAMN your dictionary is SO big now': count
    Context.bot.sendUpdate(chatId,
            "Oh yea baby... I saved it for you ;)\n\n$resultStr", afterSaveKeyboard())
}


fun processCallbackQuery(callbackQuery: CallbackQuery) {
    val text = callbackQuery.data
    val userId = callbackQuery.from.id
    val chatId: Long = userId.toLong()

    when (text) {
        "?dictionary" -> showDictionary(userId, chatId)
        "?addTranslation" -> promptTranslation(chatId)
        "?menu" -> processMenu(chatId)
        "?translate" -> promptTranslate(chatId)
        "?save" -> processSave(userId, chatId)
    }
}
