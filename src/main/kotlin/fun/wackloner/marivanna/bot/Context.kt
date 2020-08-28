package `fun`.wackloner.marivanna.bot

import `fun`.wackloner.marivanna.managers.ExpressionManager
import `fun`.wackloner.marivanna.model.SimpleTranslation
import `fun`.wackloner.marivanna.repositories.ExpressionRepository
import `fun`.wackloner.marivanna.services.TranslationService
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class Context {
    companion object {
        lateinit var appContext: ApplicationContext

        val bot: Bot
            get() = appContext.getBean(Bot::class.java)

        val expressionManager: ExpressionManager
            get() = appContext.getBean(ExpressionManager::class.java)

        val expressionRepository: ExpressionRepository
            get() = appContext.getBean(ExpressionRepository::class.java)

        val translationService: TranslationService
            get() = appContext.getBean(TranslationService::class.java)

        // TODO: make thread-safe
        // TODO: per-user
        var waitingForTranslation: Boolean = false
        var waitingForTranslate: Boolean = false
        var actionMessageId: Int? = null

        var lastTranslation: SimpleTranslation? = null
    }
}