package `fun`.wackloner.marivanna.bot

import `fun`.wackloner.marivanna.model.Emoji
import `fun`.wackloner.marivanna.utils.menuKeyboard

fun promptTranslation(chatId: Long) {
    Context.bot.sendUpdate(
            chatId,
            "Send me a new translation, Darling ${Emoji.SEND_KISS}\nFor example:\n\n<i>This bot is sooo cool — этот бот таак хорош; это бот быть тааак круто</i>",
            menuKeyboard()
    )
    Context.waitingForTranslation = true
}

fun promptTranslate(chatId: Long) {
    Context.bot.sendUpdate(chatId,
            "Hey, sexy, I'll translate everything for you${Emoji.WINKING}\n\n<i>Enter a word/phrase:</i>", menuKeyboard())
    Context.waitingForTranslate = true
}
