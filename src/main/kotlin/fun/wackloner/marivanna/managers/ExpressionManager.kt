package `fun`.wackloner.marivanna.managers

import `fun`.wackloner.marivanna.bot.Bot
import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.model.SimpleTranslation
import `fun`.wackloner.marivanna.model.Expression
import `fun`.wackloner.marivanna.repositories.ExpressionRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service


fun parseTranslation(text: String, userId: Int, chatId: Long): SimpleTranslation? {
    val delimiter = when {
        text.contains("—") -> "—"
        text.contains("--") -> "--"
        text.contains(" - ") -> " - "
        else -> return null
    }

    // TODO: handle bad hyphens
    val (expression, translation) = text.split(delimiter)
    return SimpleTranslation(expression.trim(), translation.trim(),
            Context.forChat(chatId).sourceLanguage, Context.forChat(chatId).destLanguage)
}

@Service
class ExpressionManager(val repository: ExpressionRepository) {
    companion object {
        val logger = KotlinLogging.logger {}
    }

    val userExpressions: MutableMap<Int, MutableList<Expression>> = HashMap()

    fun addTranslationsFromString(text: String, userId: Int, chatId: Long): List<SimpleTranslation> {
        // TODO: extract language from text or verify within settings
        val newTranslations = text.toLowerCase().split("\n").mapNotNull { parseTranslation(it, userId, chatId) }

        return newTranslations.mapNotNull { try {
            if (addTranslation(userId, chatId, it) == null) null else it
        } catch (e: Exception) {
            logger.error { e }
            null
        } }
    }

    // TODO: add batch-version with group by
    fun addTranslation(userId: Int, chatId: Long, text: String, sourceLang: String, translation: String, destLang: String): Expression? {
        val existing = repository.findByUserIdAndText(userId, text)

        val curExpression = when (existing.size) {
            0 -> Expression(userId, text, sourceLang)
            1 -> existing[0]
            else -> {
                // TODO: handle better (wtf situation)
                Bot.logger.error { "\n\n\nExpressions with the same text: $existing" }
                existing[0]
            }
        }
        val updated = curExpression.withNewTranslation(translation, destLang)

        // TODO: handle duplicates differently (at least tell about them)
        val res = repository.save(updated)

        userExpressions[userId]?.removeIf { it.text == text }
        userExpressions[userId]?.add(res)

        return res
    }

    fun addTranslation(userId: Int, chatId: Long, t: SimpleTranslation): Expression? =
            addTranslation(userId, chatId, t.expression, t.sourceLang, t.translation, t.destLang)

    fun getUserExpressions(userId: Int): MutableList<Expression> {
        var res = userExpressions[userId]
        if (res == null) {
            res = repository.findByUserId(userId).toMutableList()
            userExpressions[userId] = res
        }
        return res
    }
}