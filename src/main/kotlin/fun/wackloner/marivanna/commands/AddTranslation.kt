package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.managers.TranslationManager
import org.telegram.telegrambots.meta.api.objects.Message


class AddTranslationCommand : KoreshCommand("add_translation", "add new translation") {
    override fun process(message: Message, arguments: Array<out String>) {
        val text = message.text
        if (!text.contains(" - ")) {
            sendText(message.chatId, "Тире ннада..")
            return
        }

        val parts = text.split(" - ")
        TranslationManager.add(parts[0], parts[1])

        val response = TranslationManager.getAll().joinToString("\n")
        sendText(message.chatId, response)
    }

}