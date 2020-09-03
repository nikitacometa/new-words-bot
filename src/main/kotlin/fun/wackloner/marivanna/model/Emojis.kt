package `fun`.wackloner.marivanna.model


class Emojis {
    companion object {
        const val SEND_KISS= "\uD83D\uDE18"
        const val LOVE_FACE = "\uD83E\uDD70"
        const val WINKING = "\uD83D\uDE09"

        const val DANCING_GIRL = "\uD83D\uDC83"
        const val SURFER = "\uD83C\uDFC4"

        const val PENCIL = "\uD83D\uDD8C"
        const val RED_BOOK = "\uD83D\uDCD5"
        const val FLOPPY = "\uD83D\uDCBE"
        const val ROCKET = "\uD83D\uDE80"
        const val CRYSTAL_BALL = "\uD83D\uDD2E"
        const val SPIRAL = "\uD83C\uDF00"
        const val WRENCH = "\uD83D\uDD27"

        const val COUNTER_CLOCKWISE = "\uD83D\uDD04"

        const val RIGHT_ARROW = "▶️"
        const val LEFT_ARROW = "◀️️"

        fun flag(countryCode: String): String = when(countryCode.toLowerCase()) {
            "en" -> "\uD83C\uDDFA\uD83C\uDDF8"
            "ua" -> "\uD83C\uDDFA\uD83C\uDDE6"
            "ru" -> "\uD83C\uDDF7\uD83C\uDDFA"
            else -> "\uD83C\uDE2F️" // neutral flag
        }
    }
}
