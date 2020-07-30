package `fun`.wackloner.marivanna.managers

data class Translation(val source: String, val translation: String)

object TranslationManager {
    private val translations: MutableList<Translation> = mutableListOf()

    fun add(source: String, translation: String) = translations.add(Translation(source, translation))

    fun getAll(): List<Translation> = translations
}