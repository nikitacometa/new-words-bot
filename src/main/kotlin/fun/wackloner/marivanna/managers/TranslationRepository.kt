package `fun`.wackloner.marivanna.managers

import `fun`.wackloner.marivanna.Translation
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TranslationRepository : MongoRepository<Translation, ObjectId> {
    override fun <T : Translation> save(t: T): T

    fun findByUserId(userId: Int): List<Translation>
}
