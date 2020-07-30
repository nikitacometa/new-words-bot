package `fun`.wackloner.marivanna

import mu.KotlinLogging
import org.apache.http.client.utils.URIBuilder
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.annotation.PostConstruct


@Component
class Bot(private val applicationContext: ApplicationContext) : TelegramLongPollingCommandBot() {
    companion object {
        const val TELEGRAM_BASE_URL = "https://api.telegram.org"

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

        val commands: Map<IBotCommand, Boolean> = emptyMap()
//        val commands = applicationContext.getBeansOfType(IBotCommand.class) // TODO: swap this line and the prev one
        registerAll(*commands.keys.toTypedArray())
    }

    // TODO: provide settings for it
    private val httpClient: HttpClient = HttpClient.newHttpClient()

    fun sendText(chat_id: Long, text: String): Boolean {
        val uriBuilder = URIBuilder("$TELEGRAM_BASE_URL/bot${Settings.API_TOKEN}/sendMessage")
                .addParameter("chat_id", chat_id.toString())
                .addParameter("text", text)

        val request = HttpRequest.newBuilder(uriBuilder.build()).build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        logger.debug { "${response.statusCode()}: ${response.body()}" }

        return response.statusCode() == 200
    }

    override fun processNonCommandUpdate(update: Update) {
        sendText(update.message.chatId, "oh hi mark")
    }

    override fun getBotUsername(): String = Settings.BOT_USERNAME

    override fun getBotToken(): String = Settings.API_TOKEN

}
