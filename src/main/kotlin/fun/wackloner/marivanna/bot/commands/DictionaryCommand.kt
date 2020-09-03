package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.bot.resetInputRequests
import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.model.Operations
import `fun`.wackloner.marivanna.utils.dictionaryKeyboard
import `fun`.wackloner.marivanna.utils.emptyDictionaryKeyboard
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

object Order {
    const val ASC = "ASC"
    const val DESC = "DESC"
}

object OrderCriteria {
    const val ALPHABET = "ALPHABET"
    const val DATE = "DATE"
}

// TODO: add button to remove entry: for showing items buttons 'whole|1|2'

// TODO: buttons to change sorting
fun showDictionary(userId: Int, chatId: Long) {
    // TODO: refactor to use decorators
    resetInputRequests(chatId)

    // TODO: make it thread-safe
    val expressions = Context.expressionManager.getUserExpressions(userId)
    if (expressions.isEmpty()) {
        Context.bot.sendUpdate(chatId, "Your dictionary is empty(\nLet's fill it?", emptyDictionaryKeyboard(chatId))
        return
    }

    // TODO: maybe use another sort (setting)
    expressions.sortBy { it.text }

    val pageNum = Context.forChat(chatId).curDictPage
    val min = pageNum * Settings.DICT_PAGE_SIZE + 1
    val max = ((pageNum + 1) * Settings.DICT_PAGE_SIZE).coerceAtMost(expressions.size)
    val title = "${Emojis.RED_BOOK} <u><b>Dictionary</b></u> | <u><b>$min - $max</b></u> ${Emojis.RED_BOOK}"
    val thisPage = expressions.subList(min - 1, max).joinToString("\n\n") { it.beautifulHtml() }
    Context.bot.sendUpdate(chatId, "$title\n\n$thisPage", dictionaryKeyboard(chatId, expressions.size, pageNum))
}

fun tryChangePage(op: String, userId: Int, chatId: Long): Boolean {
    var curPage = Context.forChat(chatId).curDictPage
    val oldPage = curPage
    val lastPage = (Context.expressionManager.getUserExpressions(userId).size - 1) / Settings.DICT_PAGE_SIZE
    when (op) {
        Operations.LEFT -> curPage--
        Operations.RIGHT -> curPage++
        Operations.BEGIN -> curPage = 0
        Operations.END -> curPage = lastPage
        else -> return false
    }
    Context.forChat(chatId).curDictPage = curPage.coerceIn(0, lastPage)
    if (curPage != oldPage) {
        showDictionary(userId, chatId)
    }
    return true
}

@Component
class DictionaryCommand : KoreshCommand("dictionary", "show current dictionary, modify it") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        showDictionary(message.from.id, message.chatId)
    }
}