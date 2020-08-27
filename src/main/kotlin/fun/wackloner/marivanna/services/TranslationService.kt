package `fun`.wackloner.marivanna.services

import `fun`.wackloner.marivanna.Settings
import `fun`.wackloner.marivanna.Translation
import org.springframework.stereotype.Service

// TODO: implement
@Service
class TranslationService {
    fun translate(text: String, destLang: String): Translation =
            Translation(text, listOf("firstTranslation", "secondTranslation"), Settings.LEARNING_LANGUAGE, destLang)
}