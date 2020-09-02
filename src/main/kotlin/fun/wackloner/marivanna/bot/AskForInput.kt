package `fun`.wackloner.marivanna.bot

import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.utils.dictionaryOrMenu

fun promptTranslation(chatId: Long) {
    // TODO: remove again button just wait for another input
    Context.bot.sendUpdate(chatId,
            "Send me new translations, darling${Emojis.SEND_KISS} One per line, like:\n\n" +
                    "<i>cat — киса\nThis bot is sooo cool — это бот быть тааак круто</i>",
            dictionaryOrMenu()
    )
    Context.waitingForTranslation = true
}

fun promptTranslate(chatId: Long) {
    // TODO: add button to switch languages
    Context.bot.sendUpdate(chatId, "<i>Enter a word/phrase:</i>", dictionaryOrMenu())
    Context.waitingForTranslate = true
}

fun resetInputRequests() {
    Context.waitingForTranslation = false
    Context.waitingForTranslate = false
}
