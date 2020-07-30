package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.managers.Translation
import `fun`.wackloner.marivanna.managers.TranslationRepository
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message


@Component
class AddTranslationCommand : KoreshCommand("add_translation", "add new translation") {

    @Autowired
    private lateinit var translationRepository: TranslationRepository

    override fun process(message: Message, arguments: Array<out String>) {
        val text = arguments.joinToString(" ")
        if (!text.contains(" - ")) {
            sendText(message.chatId, "Тире ннада..")
            return
        }

        val parts = text.split(" - ")
        translationRepository.insert(Translation(null, parts[0], parts[1]))

        val response = translationRepository.getAll(Pageable.unpaged()).joinToString("\n")
        sendText(message.chatId, response)
    }

}
