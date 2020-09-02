package `fun`.wackloner.marivanna.bot

import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.bot.commands.KoreshCommand
import `fun`.wackloner.marivanna.utils.mainMenuKeyboard
import `fun`.wackloner.marivanna.bot.handlers.processCallbackQuery
import `fun`.wackloner.marivanna.bot.handlers.tryProcessCommandData
import mu.KotlinLogging
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import javax.annotation.PostConstruct


@Component
class Bot(
        val applicationContext: ApplicationContext
) : TelegramLongPollingCommandBot() {

    companion object {
        init {
            ApiContextInitializer.init()
        }

        val logger = KotlinLogging.logger {}
    }

    @PostConstruct
    fun register() {
        val telegramBotsApi = TelegramBotsApi()
        try {
            telegramBotsApi.registerBot(this)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }

        Context.appContext = applicationContext
        val commands = applicationContext.getBeansOfType(KoreshCommand::class.java)
        registerAll(*commands.values.toTypedArray())
    }

    fun sendText(
            chatId: Long,
            text: String,
            keyboardMarkup: InlineKeyboardMarkup? = null,
            parseMode: String? = ParseMode.HTML
    ): Message {
        val message = execute(SendMessage()
                .setText(text)
                .setChatId(chatId)
                .setReplyMarkup(keyboardMarkup)
                .setParseMode(parseMode)
        )
        Context.forChat(chatId).actionMessageId = message.messageId
        return message
    }

    fun editLastMessage(
            chatId: Long,
            text: String,
            keyboardMarkup: InlineKeyboardMarkup? = null,
            parseMode: String? = ParseMode.HTML
    ): Message? {
        if (Context.forChat(chatId).actionMessageId == null)
            return null

        val res = execute(EditMessageText()
                .setMessageId(Context.forChat(chatId).actionMessageId)
                .setText(text)
                .setChatId(chatId)
                .setReplyMarkup(keyboardMarkup)
                .setParseMode(parseMode)
        )
        return res as Message
    }

    fun sendUpdate(
            chatId: Long,
            text: String,
            keyboardMarkup: InlineKeyboardMarkup? = null,
            parseMode: String? = ParseMode.HTML
    ): Message = editLastMessage(chatId, text, keyboardMarkup, parseMode)
            ?: sendText(chatId, text, keyboardMarkup, parseMode)

    override fun processNonCommandUpdate(update: Update) {
        logger.info { update }

        if (update.hasCallbackQuery()) {
            processCallbackQuery(update.callbackQuery)
            return
        }

        Context.forChat(update.message.chatId).actionMessageId = null

        if (tryProcessCommandData(update.message)) {
            return
        }

        sendText(update.message.chatId,
                "Sorry, I don't understand... What do you want me to do?${Emojis.WINKING}",
                mainMenuKeyboard(update.message.chatId))
    }

    override fun getBotUsername(): String = Settings.BOT_USERNAME

    override fun getBotToken(): String = Settings.API_TOKEN

}
