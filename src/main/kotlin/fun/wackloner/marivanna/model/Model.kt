package `fun`.wackloner.marivanna.model

import `fun`.wackloner.marivanna.utils.formatSingleTranslation
import org.bson.types.ObjectId
import org.joda.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.lang.RuntimeException

class Emoji {
    companion object {
        const val PAPER = "\uD83D\uDCCB"
        const val SEND_KISS= "\uD83D\uDE18"
        const val LOVE_FACE = "\uD83E\uDD70"
        const val WINKING = "\uD83D\uDE09"
    }
}

data class Translation(val phrase: String, val translated: String, val sourceLang: String, val destLang: String)

@Document
data class UserTranslation(
        val userId: Int,
        val phrase: String,
        val lang: String,
        val translations: Map<String, List<String>>,
        @Id val id: ObjectId = ObjectId.get(),
        val createdDate: LocalDateTime = LocalDateTime.now(),
        val modifiedDate: LocalDateTime = LocalDateTime.now()
) {
    fun beautifulHtml(): String {
        val allLangsTranslations = translations.values.flatten()
        return when(allLangsTranslations.size) {
            0 -> throw RuntimeException("Phrase without translations WTF? $this")
            1 -> formatSingleTranslation(phrase, allLangsTranslations[0])
            else -> "${Emoji.PAPER} <b>${phrase}</b>:\n" +
                    allLangsTranslations.withIndex().joinToString("\n") { (i, t) -> "<i>${i + 1}) $t</i>" }
        }
    }

}
