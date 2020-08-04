package `fun`.wackloner.marivanna.managers

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

@Document
data class Translation(
        val source: String,
        val translation: String,
        val userId: Int,
        @Id val id: ObjectId = ObjectId.get(),
        val createdDate: LocalDateTime = LocalDateTime.now(),
        val modifiedDate: LocalDateTime = LocalDateTime.now()
)

interface TranslationRepository : MongoRepository<Translation, String> {
    override fun <T : Translation> save(t: T): T

    fun findByUserId(userId: Int): List<Translation>
}
