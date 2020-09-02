package `fun`.wackloner.marivanna.bot

import java.util.*


object Settings {
    private val properties: Properties by lazy {
        val stream = this::class.java.classLoader.getResource("application.properties")?.openStream()
        val res = Properties()
        res.load(stream)
        res
    }

    val API_TOKEN: String = properties.getProperty("bot.api_token")
    val BOT_USERNAME: String = properties.getProperty("bot.username")

    const val MAX_TEXT_LENGTH: Int = 1000
}
