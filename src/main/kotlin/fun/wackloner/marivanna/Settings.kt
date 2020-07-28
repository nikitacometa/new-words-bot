package `fun`.wackloner.marivanna

import java.util.*


object Settings {
    val properties: Properties by lazy {
        val stream = this::class.java.classLoader.getResource("application.properties")?.openStream()
        val res = Properties()
        res.load(stream)
        res.forEach{(k, v) -> println("key = $k, value = $v")}
        res
    }

    val API_TOKEN: String = properties.getProperty("bot.api_token")
}
