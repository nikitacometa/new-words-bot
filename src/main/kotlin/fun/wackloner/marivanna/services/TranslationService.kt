package `fun`.wackloner.marivanna.services

import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.SimpleTranslation
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import org.springframework.stereotype.Service


@Service
class TranslationService {
    fun translate(text: String, destLang: String): SimpleTranslation {
        val translate: Translate = TranslateOptions.getDefaultInstance().service
        val translation = translate.translate(text, Translate.TranslateOption.targetLanguage(Settings.NATIVE_LANGUAGE))
        return SimpleTranslation(text, translation.translatedText, translation.sourceLanguage, Settings.NATIVE_LANGUAGE)
    }
}
