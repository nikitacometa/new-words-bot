package `fun`.wackloner.marivanna.bot

import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.utils.menuKeyboard

fun promptTranslation(chatId: Long) {
    Context.bot.sendUpdate(chatId,
            "Send me a new translation, Darling ${Emojis.SEND_KISS}\nFor example:\n\n" +
                    "<i>This bot is sooo cool — этот бот таак хорош; это бот быть тааак круто</i>",
            menuKeyboard()
    )
    Context.waitingForTranslation = true
}

fun promptTranslate(chatId: Long) {
    // TODO: add button to switch languages
    Context.bot.sendUpdate(chatId,
            "Hey, sexy, I'll translate everything for you${Emojis.WINKING}\n\n<i>Enter a word/phrase:</i>", menuKeyboard())
    Context.waitingForTranslate = true
}

fun resetInputRequests() {
    Context.waitingForTranslation = false
    Context.waitingForTranslate = false
}
