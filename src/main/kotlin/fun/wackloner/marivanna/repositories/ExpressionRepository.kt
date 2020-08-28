package `fun`.wackloner.marivanna.repositories

import `fun`.wackloner.marivanna.model.Expression
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ExpressionRepository : MongoRepository<Expression, ObjectId> {
    override fun <T : Expression> save(t: T): T

    fun findByUserId(userId: Int): List<Expression>

    fun findByUserIdAndText(userId: Int, expression: String): List<Expression>
}
