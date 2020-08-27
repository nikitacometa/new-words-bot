package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Bot
import mu.KotlinLogging
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender


abstract class KoreshCommand(private val name: String, private val helpString: String) : IBotCommand {
    companion object {
        val logger = KotlinLogging.logger {}
    }

    abstract fun process(bot: Bot, message: Message, arguments: Array<String>)

    override fun getCommandIdentifier(): String = name

    override fun getDescription(): String = helpString

    override fun processMessage(absSender: AbsSender, message: Message, args: Array<String>) {
        logger.info { message }

        return process(absSender as Bot, message, args)
    }
}