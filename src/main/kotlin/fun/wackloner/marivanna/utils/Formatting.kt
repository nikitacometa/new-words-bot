package `fun`.wackloner.marivanna.utils

import `fun`.wackloner.marivanna.model.Emojis

fun formatSingleTranslation(text: String, translation: String): String =
        "${Emojis.PAPER} <b>$text</b> — <i>$translation</i>"