package `fun`.wackloner.marivanna

import org.bson.types.ObjectId
import org.joda.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

class Emoji {
    companion object {
        const val PAPER = "\uD83D\uDCCB"
        const val SEND_KISS= "\uD83D\uDE18"
        const val LOVE_FACE = "\uD83E\uDD70"
        const val WINKING = "\uD83D\uDE09"
    }
}

data class Phrase(
        val text: String,
        // TODO: use enum or something
        val lang: String
)

data class Translation(val phrase: String, val translations: List<String>, val sourceLang: String, val destLang: String)

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
    fun beautifulHtml(): String = "${Emoji.PAPER} <b>${phrase}</b>:\n" +
            translations.values.flatten().withIndex().joinToString("\n") { (i, t) -> "<i>${i + 1}) $t</i>" }

}
