package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context

fun processSwapLanguages(chatId: Long) {
    val tmp = Context.forChat(chatId).sourceLanguage
    Context.forChat(chatId).sourceLanguage = Context.forChat(chatId).destLanguage
    Context.forChat(chatId).destLanguage = tmp
    Context.forChat(chatId).languagesSwapped = !Context.forChat(chatId).languagesSwapped
    promptTranslate(chatId)
}
