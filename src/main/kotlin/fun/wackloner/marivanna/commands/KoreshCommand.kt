package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.Bot
import `fun`.wackloner.marivanna.Settings
import mu.KotlinLogging
import org.apache.http.client.utils.URIBuilder
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class KoreshCommand(private val name: String, private val helpString: String) : IBotCommand {
    companion object {
        protected val logger = KotlinLogging.logger {}
    }

    abstract fun process(message: Message, arguments: Array<out String>)

    private val httpClient: HttpClient = HttpClient.newHttpClient()

    // TODO: extract to AbsSender impl
    protected fun sendText(chat_id: Long, text: String): Boolean {
        val uriBuilder = URIBuilder("${Bot.TELEGRAM_BASE_URL}/bot${Settings.API_TOKEN}/sendMessage")
                .addParameter("chat_id", chat_id.toString())
                .addParameter("text", text)

        val request = HttpRequest.newBuilder(uriBuilder.build()).build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        logger.debug { "${response.statusCode()}: ${response.body()}" }

        return response.statusCode() == 200
    }

    override fun getCommandIdentifier(): String = name

    override fun getDescription(): String = helpString

    override fun processMessage(absSender: AbsSender, message: Message, arguments: Array<out String>) = process(message, arguments)
}