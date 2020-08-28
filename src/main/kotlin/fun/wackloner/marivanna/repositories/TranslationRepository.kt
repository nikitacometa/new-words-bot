package `fun`.wackloner.marivanna.repositories

import `fun`.wackloner.marivanna.model.Translation
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TranslationRepository : MongoRepository<Translation, ObjectId> {
    override fun <T : Translation> save(t: T): T

    fun findByUserId(userId: Int): List<Translation>

    fun findByUserIdAndExpression(userId: Int, expression: String): List<Translation>
}
