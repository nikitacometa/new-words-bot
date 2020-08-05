package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.*

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


fun learning(text: String): Phrase = Phrase(text, Settings.LEARNING_LANGUAGE)

fun native(vararg texts: String): List<Phrase> = texts.map { Phrase(it.trim(), Settings.NATIVE_LANGUAGE) }

fun addTranslation(text: String, userId: Int): Translation {
    val (learningText, translations) = text.split(" - ")

    // TODO: extract language or verify within settings
    val newTranslations = native(*translations.split(";").toTypedArray())

    // TODO: update if exists
    // TODO: filter duplicates
    val newObject = AppContext.translationRepository.save(Translation(learning(learningText), newTranslations, userId))

    // TODO: check if res is OK
    return newObject
}

@Component
class AddTranslationCommand : KoreshCommand("add_translation", "add a new translation") {

    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val wholeText = arguments.joinToString(" ")
        if (!wholeText.contains(" - ")) {
            bot.sendText(message.chatId, "Тире ннада.")
            return
        }

        try {
            val newTranslation = addTranslation(wholeText, message.from.id)
            bot.sendText(message.chatId, newTranslation.beautifulHtml())
        } catch (e: Exception) {
            logger.error { e }
        }
    }

}
