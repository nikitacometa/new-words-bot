package `fun`.wackloner.marivanna.bot

import `fun`.wackloner.marivanna.managers.ChatManager
import `fun`.wackloner.marivanna.managers.ExpressionManager
import `fun`.wackloner.marivanna.managers.UserManager
import `fun`.wackloner.marivanna.model.ChatData
import `fun`.wackloner.marivanna.repositories.ExpressionRepository
import `fun`.wackloner.marivanna.services.TranslationService
import org.springframework.context.ApplicationContext


fun resetInputRequests(chatId: Long) {
    Context.forChat(chatId).waitingForTranslation = false
    Context.forChat(chatId).waitingForTranslate = false
}

object Context {
    lateinit var appContext: ApplicationContext

    val bot: Bot
        get() = appContext.getBean(Bot::class.java)

    val expressionManager: ExpressionManager
        get() = appContext.getBean(ExpressionManager::class.java)

    val expressionRepository: ExpressionRepository
        get() = appContext.getBean(ExpressionRepository::class.java)

    val translationService: TranslationService
        get() = appContext.getBean(TranslationService::class.java)

    val userManager: UserManager
        get() = appContext.getBean(UserManager::class.java)

    val chatManager: ChatManager
        get() = appContext.getBean(ChatManager::class.java)

    fun forChat(chatId: Long): ChatData = chatManager.getOrCreate(chatId)

    fun forChat(chatId: Long?): ChatData = if (chatId == null) chatManager.getOrCreate(0) else forChat(chatId)
}
