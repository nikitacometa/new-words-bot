package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.resetInputRequests
import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.utils.mainMenuKeyboard


fun processMenu(chatId: Long) {
    resetInputRequests()

    Context.bot.sendUpdate(chatId, "I'll do whatever you want${Emojis.LOVE_FACE}", mainMenuKeyboard())
}
