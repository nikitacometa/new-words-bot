package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.AppContext
import `fun`.wackloner.marivanna.Emoji
import `fun`.wackloner.marivanna.Settings
import `fun`.wackloner.marivanna.UserTranslation
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

fun processMenu(text: String, chatId: Long) {
    // TODO: also set them false on other commands
    if (AppContext.waitingForTranslation) {
        AppContext.waitingForTranslation = false
    }
    if (AppContext.waitingForTranslate) {
        AppContext.waitingForTranslate = false
    }

    AppContext.bot.sendUpdate(chatId, "I will do whatever you want${Emoji.LOVE_FACE} (not really)", mainMenuKeyboard())
}

fun processSave(userId: Int, chatId: Long) {
    val translation = AppContext.lastTranslation
    if (translation == null) {
        AppContext.bot.sendUpdate(chatId, "There was not translations yet... Translate something?)", mainMenuKeyboard())
        return
    }

    try {
        AppContext.translationRepository.save(UserTranslation(userId, translation.phrase,
                Settings.NATIVE_LANGUAGE, mapOf(translation.destLang to listOf(translation.translated))))
    } catch (e: Exception) {
        AppContext.bot.sendUpdate(chatId,
                "Sorry, sweetheart, I failed to save '${translation.translated}'. Should the bad girl try again?..", saveKeyboard())
        return
    }

    val resultStr = formatSingleTranslation(translation.phrase, translation.translated)
    // TODO: 'oh DAMN your dictionary is SO big now': count
    AppContext.bot.sendUpdate(chatId,
            "Oh yea baby... I saved it for you ;)\n\n$resultStr", menuKeyboard())
}


fun processCallbackQuery(callbackQuery: CallbackQuery) {
    val text = callbackQuery.data
    val userId = callbackQuery.from.id
    val chatId: Long = userId.toLong()

    when (text) {
        "?dictionary" -> showDictionary(userId, chatId)
        "?addTranslation" -> promptTranslation(userId, chatId)
        "?menu" -> processMenu(text, chatId)
        "?translate" -> promptTranslate(userId, chatId)
        "?save" -> processSave(userId, chatId)
    }
}
