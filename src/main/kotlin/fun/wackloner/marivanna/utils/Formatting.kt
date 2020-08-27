package `fun`.wackloner.marivanna.utils

import `fun`.wackloner.marivanna.model.Emoji

fun formatSingleTranslation(text: String, translation: String): String =
        "${Emoji.PAPER} <b>$text</b> — <i>$translation</i>"