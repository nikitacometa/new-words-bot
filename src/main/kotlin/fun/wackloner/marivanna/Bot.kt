package `fun`.wackloner.marivanna

import mu.KotlinLogging
import org.apache.http.client.utils.URIBuilder
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


@Component
class Bot : TelegramLongPollingBot() {
    companion object {
        const val TELEGRAM_BASE_URL = "https://api.telegram.org"

        private val logger = KotlinLogging.logger {}
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

    override fun onUpdateReceived(update: Update?) {
        if (update == null)
            return

        logger.debug { "New update: ${update.message.text}" }
        sendText(update.message.chat.id, "o hi mark")
    }

    override fun getBotUsername(): String {
        return Settings.properties.getProperty("bot.username")
    }

    override fun getBotToken(): String {
        return Settings.API_TOKEN
    }
}
