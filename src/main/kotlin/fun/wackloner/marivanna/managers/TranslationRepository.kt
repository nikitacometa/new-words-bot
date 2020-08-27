package `fun`.wackloner.marivanna.managers

import `fun`.wackloner.marivanna.UserTranslation
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TranslationRepository : MongoRepository<UserTranslation, ObjectId> {
    override fun <T : UserTranslation> save(t: T): T

    fun findByUserId(userId: Int): List<UserTranslation>
}
