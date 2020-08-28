package `fun`.wackloner.marivanna.managers

import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.SimpleTranslation
import `fun`.wackloner.marivanna.model.Translation
import `fun`.wackloner.marivanna.repositories.TranslationRepository
import org.springframework.stereotype.Service


data class SplitResult(val expression: String, val translations: String)

fun splitTranslationsText(text: String): SplitResult? {
    val delimiter = when {
        text.contains("—") -> "—"
        text.contains(" - ") -> " - "
        else -> return null
    }

    val (expression, translations) = text.split(delimiter)
    return SplitResult(expression.trim(), translations)
}

@Service
class TranslationManager(val repository: TranslationRepository) {
    fun addTranslationsFromString(text: String, userId: Int): Translation? {
        val split = splitTranslationsText(text) ?: return null

        // TODO: extract language or verify within settings
        val newTranslations = split.translations.split(";").map { it.trim() }

        // TODO: update if exists
        // TODO: filter duplicates
        return addTranslations(userId, split.expression, Settings.LEARNING_LANGUAGE, newTranslations, Settings.NATIVE_LANGUAGE)
    }

    fun addTranslations(userId: Int, expression: String, sourceLang: String, translations: List<String>, destLang: String): Translation? {
        val existing = repository.findByUserIdAndExpression(userId, expression)

        Bot.logger.info { "\n\n\nexp=$expression, exist=$existing" }
        Bot.logger.info { "\n\n\nall=${repository.findByUserId(userId)}" }

        val translation = when(existing.size) {
            0 -> null
            1 -> existing[0]
            else -> {
                // TODO: handle better (wtf situation)
                existing[0]
            }
        }
        if (translation == null) {
            return repository.save(Translation(userId, expression, sourceLang, mapOf(destLang to translations.toSet())))
        } else {
            val oldTranslations = translation.translations[destLang]?.toMutableSet() ?: mutableSetOf()
            val wasUpdated = oldTranslations.addAll(translations)
            if (wasUpdated) {
                return repository.save(translation.copy(
                        translations = translation.translations.plus(destLang to oldTranslations))
                )
            }
            return translation
        }
    }

    fun addTranslation(userId: Int, t: SimpleTranslation): Translation? =
            addTranslations(userId, t.expression, t.sourceLang, listOf(t.translation), t.destLang)
}