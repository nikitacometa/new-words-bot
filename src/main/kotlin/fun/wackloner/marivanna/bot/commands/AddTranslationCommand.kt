package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.model.Emoji
import `fun`.wackloner.marivanna.utils.afterAddKeyboard
import `fun`.wackloner.marivanna.utils.mainMenuKeyboard

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


fun processNewTranslations(text: String, userId: Int, chatId: Long) {
    val newTranslation = Context.translationManager.addTranslationsFromString(text, userId)
    if (newTranslation == null) {
        // TODO: 'try again?'
        Context.bot.sendUpdate(chatId, "I failed to add <i>'$text</i>...", mainMenuKeyboard())
        return
    }

    Context.bot.sendUpdate(chatId,
            "Wow, you're so smart${Emoji.WINKING}\n\n${newTranslation.beautifulHtml()}", afterAddKeyboard())
}

@Component
class AddTranslationCommand : KoreshCommand("add_translation", "add a new translation") {

    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val wholeText = arguments.joinToString(" ")

        try {
            processNewTranslations(wholeText, message.from.id, message.chatId)
        } catch (e: Exception) {
            logger.error { e }
        }
    }

}
