package `fun`.wackloner.marivanna.services

import `fun`.wackloner.marivanna.model.SimpleTranslation
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import org.springframework.stereotype.Service


@Service
class TranslationService {
    fun translate(text: String, destLang: String): SimpleTranslation {
        val translate: Translate = TranslateOptions.getDefaultInstance().service
        val translation = translate.translate(text, Translate.TranslateOption.targetLanguage(destLang))
        return SimpleTranslation(text, translation.translatedText, translation.sourceLanguage, destLang)
    }
}
