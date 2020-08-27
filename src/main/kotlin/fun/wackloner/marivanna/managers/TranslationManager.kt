package `fun`.wackloner.marivanna.managers

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.UserTranslation
import `fun`.wackloner.marivanna.repositories.TranslationRepository
import org.springframework.stereotype.Service


data class SplitResult(val phrase: String, val options: String)

fun splitTranslationsText(text: String): SplitResult? {
    val delimiter = when {
        text.contains("—") -> "—"
        text.contains(" - ") -> " - "
        else -> return null
    }

    val (phrase, options) = text.split(delimiter)
    return SplitResult(phrase, options)
}

fun native(vararg texts: String): Map<String, List<String>> =
        mutableMapOf(Settings.NATIVE_LANGUAGE to texts.toList().map { it.trim() })

@Service
class TranslationManager(val repository: TranslationRepository) {
    fun addTranslationsFromString(text: String, userId: Int): UserTranslation? {
        val split = splitTranslationsText(text) ?: return null

        // TODO: extract language or verify within settings
        val newTranslations = native(*split.options.split(";").toTypedArray())

        // TODO: update if exists
        // TODO: filter duplicates
        return Context.translationRepository.save(
                UserTranslation(userId, split.phrase, Settings.NATIVE_LANGUAGE, newTranslations))
    }
}