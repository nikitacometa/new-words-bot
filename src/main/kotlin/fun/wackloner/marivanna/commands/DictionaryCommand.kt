package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.AppContext
import `fun`.wackloner.marivanna.Bot
import `fun`.wackloner.marivanna.logger
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

fun showDictionary(userId: Int, chatId: Long) {
    logger.info { userId }

    val translations = AppContext.translationRepository.findByUserId(userId)
    val replyText = if (translations.isEmpty())
    // TODO: offer to add a new one
        "Dictionary is empty."
    else
        translations.joinToString("\n\n") { it.beautifulHtml() }

    AppContext.bot.sendUpdate(chatId, replyText, mainMenuKeyboard(false))
}

@Component
class DictionaryCommand : KoreshCommand("dictionary", "show current dictionary, modify it") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        showDictionary(message.from.id, message.chatId)
    }
}