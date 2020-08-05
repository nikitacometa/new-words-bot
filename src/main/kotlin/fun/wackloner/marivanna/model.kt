package `fun`.wackloner.marivanna

import org.bson.types.ObjectId
import org.joda.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class Phrase(
        val text: String,
        // TODO: use enum or something
        val lang: String
)

@Document
data class Translation(
        val phrase: Phrase,
        val translations: List<Phrase>,
        val userId: Int,
        @Id val id: ObjectId = ObjectId.get(),
        val createdDate: LocalDateTime = LocalDateTime.now(),
        val modifiedDate: LocalDateTime = LocalDateTime.now()
)
