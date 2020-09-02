package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.handlers.sendInProgress
import `fun`.wackloner.marivanna.bot.resetInputRequests

fun processSettings(chatId: Long) {
    resetInputRequests(chatId)
    sendInProgress(chatId)
}
