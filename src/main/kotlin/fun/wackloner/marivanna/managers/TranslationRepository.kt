package `fun`.wackloner.marivanna.managers

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document data class Translation(@Id var id: String?, val source: String, val translation: String)

interface TranslationRepository : MongoRepository<Translation, String> {
    fun insert(t: Translation): Translation

    fun getAll(p: Pageable): Page<Translation>
}
