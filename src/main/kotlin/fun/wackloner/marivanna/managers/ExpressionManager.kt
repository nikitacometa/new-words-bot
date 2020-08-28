package `fun`.wackloner.marivanna.managers

import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.SimpleTranslation
import `fun`.wackloner.marivanna.model.Expression
import `fun`.wackloner.marivanna.repositories.ExpressionRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service


fun parseTranslation(text: String): SimpleTranslation? {
    val delimiter = when {
        text.contains("—") -> "—"
        text.contains(" - ") -> " - "
        else -> return null
    }

    // TODO: handle bad hyphens
    val (expression, translation) = text.split(delimiter)
    return SimpleTranslation(expression.trim(), translation.trim(), Settings.LEARNING_LANGUAGE, Settings.NATIVE_LANGUAGE)
}

@Service
class ExpressionManager(val repository: ExpressionRepository) {
    companion object {
        val logger = KotlinLogging.logger {}
    }

    fun addTranslationsFromString(text: String, userId: Int): List<SimpleTranslation> {
        // TODO: extract language from text or verify within settings
        val newTranslations = text.split("\n").mapNotNull(::parseTranslation)

        return newTranslations.mapNotNull { try {
            if (addTranslation(userId, it) == null) null else it
        } catch (e: Exception) {
            logger.error { e }
            null
        } }
    }

    // TODO: add batch-version with group by
    fun addTranslation(userId: Int, text: String, sourceLang: String, translation: String, destLang: String): Expression? {
        val existing = repository.findByUserIdAndText(userId, text)

        val curExpression = when(existing.size) {
            0 -> Expression(userId, text, sourceLang)
            1 -> existing[0]
            else -> {
                // TODO: handle better (wtf situation)
                Bot.logger.error { "\n\n\nExpressions with the same text: $existing" }
                existing[0]
            }
        }
        val updated = curExpression.withNewTranslation(translation, destLang)

        // TODO: handle duplicates differently (at least tell about them)
        return repository.save(updated)
    }

    fun addTranslation(userId: Int, t: SimpleTranslation): Expression? =
            addTranslation(userId, t.expression, t.sourceLang, t.translation, t.destLang)
}