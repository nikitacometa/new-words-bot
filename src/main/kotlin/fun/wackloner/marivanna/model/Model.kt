package `fun`.wackloner.marivanna.model

import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import org.bson.types.ObjectId
import org.joda.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.lang.RuntimeException


data class SimpleTranslation(val expression: String, val translation: String, val sourceLang: String, val destLang: String) {
    fun swapped(): SimpleTranslation = SimpleTranslation(translation, expression, destLang, sourceLang)
}

data class TranslationOption(val text: String, val created: LocalDateTime = LocalDateTime.now()) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as TranslationOption
        return other.text == text
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }
}

@Document
data class Expression(
        val userId: Int,
        val text: String,
        val lang: String,
        val translations: Map<String, Set<TranslationOption>> = mapOf(),
        @Id val id: ObjectId = ObjectId.get(),
        val createdDate: LocalDateTime = LocalDateTime.now(),
        val modifiedDate: LocalDateTime = LocalDateTime.now()
) {
    fun beautifulHtml(): String {
        val allLangsTranslations = translations.values.flatten()
        return when(allLangsTranslations.size) {
            0 -> throw RuntimeException("Phrase without translations WTF? $this")
            1 -> formatSingleTranslation(text, allLangsTranslations[0].text)
            else -> "${Emojis.SPIRAL} <b>${text}</b>:\n" +
                    allLangsTranslations.withIndex().joinToString("\n") { (i, t) -> "<i>${i + 1}) ${t.text}</i>" }
        }
    }

    fun withNewTranslation(text: String, lang: String): Expression {
        // TODO: refactor (?)
        val newTranslations = translations.toMutableMap()
        val forLang = newTranslations.getOrPut(lang) { setOf() }
        newTranslations[lang] = forLang.plusElement(TranslationOption(text))
        return copy(translations = newTranslations)
    }
}
