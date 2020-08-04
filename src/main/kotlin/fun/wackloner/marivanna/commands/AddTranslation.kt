package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.Bot
import `fun`.wackloner.marivanna.managers.Translation
import `fun`.wackloner.marivanna.managers.TranslationRepository
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


@Component
class AddTranslationCommand : KoreshCommand("add_translation", "add new translation") {

    @Autowired
    private lateinit var translationRepository: TranslationRepository

    override fun process(bot: Bot, message: Message, arguments: Array<String>) {
        val text = arguments.joinToString(" ")
        if (!text.contains(" - ")) {
            bot.sendText(message.chatId, "Тире ннада..")
            return
        }

        val userId = message.from.id
        val parts = text.split(" - ")
        translationRepository.insert(Translation(parts[0], parts[1], userId))

        val response = translationRepository.findByUserId(userId).joinToString("\n")
        bot.sendText(message.chatId, response)
    }

}
