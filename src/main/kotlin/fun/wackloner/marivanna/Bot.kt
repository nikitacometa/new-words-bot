package `fun`.wackloner.marivanna

import `fun`.wackloner.marivanna.commands.KoreshCommand
import mu.KotlinLogging
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import javax.annotation.PostConstruct


@Component
class Bot(
        private val applicationContext: ApplicationContext
) : TelegramLongPollingCommandBot() {

    companion object {
        const val TELEGRAM_BASE_URL = "https://api.telegram.org"

        init {
            ApiContextInitializer.init()
        }

        private val logger = KotlinLogging.logger {}
    }

    @PostConstruct
    fun register() {
        val telegramBotsApi = TelegramBotsApi()
        try {
            telegramBotsApi.registerBot(this)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }

        val commands = applicationContext.getBeansOfType(KoreshCommand::class.java)
        registerAll(*commands.values.toTypedArray())
    }

    fun sendText(
            chatId: Long,
            text: String,
            keyboardMarkup: InlineKeyboardMarkup? = null,
            parseMode: String? = ParseMode.HTML
    ): Message {
        return execute(SendMessage()
                .setText(text)
                .setChatId(chatId)
                .setReplyMarkup(keyboardMarkup)
                .setParseMode(parseMode)
        )
    }

    override fun processNonCommandUpdate(update: Update) {
        logger.info { update }
        logger.info { update.message.text }

        sendText(update.message.chatId, "oh hi mark")
        sendText(update.message.chatId, Emoji.PAPER)
    }

    override fun getBotUsername(): String = Settings.BOT_USERNAME

    override fun getBotToken(): String = Settings.API_TOKEN

}
