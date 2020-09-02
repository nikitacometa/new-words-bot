package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.resetInputRequests
import `fun`.wackloner.marivanna.utils.afterDictionaryKeyboard
import `fun`.wackloner.marivanna.utils.emptyDictionaryKeyboard
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

// TODO: add button to remove entry: for showing items buttons 'whole|1|2'

// TODO: buttons to change sorting
// TODO: buttons for pages
fun showDictionary(userId: Int, chatId: Long) {
    // TODO: refactor to use decorators
    resetInputRequests(chatId)

    val translations = Context.expressionRepository.findByUserId(userId)
    if (translations.isEmpty())
        Context.bot.sendUpdate(chatId, "Your dictionary is empty(\nLet's fill it?", emptyDictionaryKeyboard(chatId))
    else
        Context.bot.sendUpdate(chatId, translations.joinToString("\n\n") { it.beautifulHtml() }, afterDictionaryKeyboard(chatId))
}

@Component
class DictionaryCommand : KoreshCommand("dictionary", "show current dictionary, modify it") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        showDictionary(message.from.id, message.chatId)
    }
}