package `fun`.wackloner.marivanna

import `fun`.wackloner.marivanna.managers.TranslationRepository
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
    }
}