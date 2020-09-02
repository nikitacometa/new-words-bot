package `fun`.wackloner.marivanna.model

import `fun`.wackloner.marivanna.bot.commands.Quiz

data class ChatData(
        val id: Long,

        var nativeLanguage: String = Languages.RU,
        var learningLanguage: String = Languages.EN,
        var sourceLanguage: String = Languages.EN,
        var destLanguage: String = Languages.RU,
        var languagesSwapped: Boolean = false,

        var waitingForTranslation: Boolean = false,
        var waitingForTranslate: Boolean = false,
        var waitingForAnswer: Boolean = false,

        var actionMessageId: Int? = null,
        var lastTranslation: SimpleTranslation? = null,

        var currentQuiz: Quiz? = null,
        var questionsInARow: Int = 0
) {
}