package `fun`.wackloner.marivanna.model

data class ChatData(
        val id: Long,

        var learningLanguage: String = Languages.EN,
        var sourceLanguage: String = Languages.EN,
        var destLanguage: String = Languages.RU,

        var waitingForTranslation: Boolean = false,
        var waitingForTranslate: Boolean = false,

        var actionMessageId: Int? = null,
        var lastTranslation: SimpleTranslation? = null
) {
}