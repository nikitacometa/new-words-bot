package `fun`.wackloner.marivanna.bot.handlers

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.commands.processAddTranslations
import `fun`.wackloner.marivanna.bot.commands.processTranslate
import org.telegram.telegrambots.meta.api.objects.Message

fun tryProcessCommandData(message: Message): Boolean {
    if (Context.forChat(message.chatId).waitingForTranslation) {
        processAddTranslations(message.text.toLowerCase(), message.from.id, message.chatId)
        return true
    }

    // TODO: maybe allow storing camel case as well (?)
    if (Context.forChat(message.chatId).waitingForTranslate) {
        processTranslate(message.text.toLowerCase(), message.chatId)
        return true
    }

    return false
}
