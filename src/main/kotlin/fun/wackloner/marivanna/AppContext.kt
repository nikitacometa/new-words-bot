package `fun`.wackloner.marivanna

import `fun`.wackloner.marivanna.managers.TranslationRepository
import `fun`.wackloner.marivanna.services.TranslationService
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class AppContext {
    companion object {
        lateinit var ctx: ApplicationContext

        val bot: Bot
            get() = ctx.getBean(Bot::class.java)

        val translationRepository: TranslationRepository
            get() = ctx.getBean(TranslationRepository::class.java)

        val translationService: TranslationService
            get() = ctx.getBean(TranslationService::class.java)

        // TODO: make thread-safe
        // TODO: per-user
        var waitingForTranslation: Boolean = false
        var waitingForTranslate: Boolean = false
        var actionMessageId: Int? = null

        var lastTranslation: Translation? = null
    }
}