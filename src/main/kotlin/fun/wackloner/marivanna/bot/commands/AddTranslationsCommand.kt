package `fun`.wackloner.marivanna.bot.commands


import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.utils.dictionaryOrMenu
import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


fun promptTranslation(chatId: Long) {
    // TODO: remove again button just wait for another input
    Context.bot.sendUpdate(chatId,
            "Send me new translations, darling${Emojis.SEND_KISS} One per line, like:\n\n" +
                    "<i>cat — киса\nThis bot is sooo cool — это бот быть тааак круто</i>",
            dictionaryOrMenu()
    )
    Context.forChat(chatId).waitingForTranslation = true
}

fun processAddTranslations(text: String, userId: Int, chatId: Long) {
    val newTranslations = Context.expressionManager.addTranslationsFromString(text, userId, chatId)
    if (newTranslations.isEmpty()) {
        Context.bot.sendUpdate(chatId, "Sorry, my love, I failed to add the translation... Try again?", dictionaryOrMenu())
        return
    }

    val translationsStr = newTranslations.joinToString("\n") { formatSingleTranslation(it.expression, it.translation) }
    Context.bot.sendUpdate(chatId, "Wow, you're so smart${Emojis.WINKING}\n\n<i>New translations:</i>\n" +
            "$translationsStr\n\n<i>Enter more translations:</i>", dictionaryOrMenu())
}

@Component
class AddTranslationsCommand : KoreshCommand("add_translations", "add new translations") {
    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val wholeText = arguments.joinToString(" ")

        try {
            processAddTranslations(wholeText, message.from.id, message.chatId)
        } catch (e: Exception) {
            logger.error { e }
        }
    }
}
