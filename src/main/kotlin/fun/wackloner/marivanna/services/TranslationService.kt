package `fun`.wackloner.marivanna.services

import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.Translation
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import org.springframework.stereotype.Service


@Service
class TranslationService {
    fun translate(text: String, destLang: String): Translation {
        val translate: Translate = TranslateOptions.getDefaultInstance().service
        val translation = translate.translate(text, Translate.TranslateOption.targetLanguage(Settings.NATIVE_LANGUAGE))
        return Translation(text, translation.translatedText, translation.sourceLanguage, Settings.NATIVE_LANGUAGE)
    }
}
