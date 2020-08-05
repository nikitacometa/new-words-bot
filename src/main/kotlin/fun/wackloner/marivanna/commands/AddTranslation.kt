package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.Bot
import `fun`.wackloner.marivanna.Phrase
import `fun`.wackloner.marivanna.Settings
import `fun`.wackloner.marivanna.Translation
import `fun`.wackloner.marivanna.managers.TranslationRepository
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


fun learning(text: String): Phrase = Phrase(text, Settings.LEARNING_LANGUAGE)

fun native(vararg texts: String): List<Phrase> = texts.map { Phrase(it, Settings.NATIVE_LANGUAGE) }

@Component
class AddTranslationCommand : KoreshCommand("add_translation", "add a new translation") {

    @Autowired
    private lateinit var translationRepository: TranslationRepository

    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val wholeText = arguments.joinToString(" ")
        if (!wholeText.contains(" - ")) {
            bot.sendText(message.chatId, "Тире ннада..")
            return
        }

        val userId = message.from.id
        val (learningText, translations) = wholeText.split(" - ")

        // TODO: extract language or verify within settings
        val newTranslations = native(*translations.split(";").toTypedArray())
        // TODO: filter duplicates
        translationRepository.insert(Translation(learning(learningText), newTranslations, userId))

        val response = translationRepository.findByUserId(userId).joinToString("\n")
        bot.sendText(message.chatId, response)
    }

}
