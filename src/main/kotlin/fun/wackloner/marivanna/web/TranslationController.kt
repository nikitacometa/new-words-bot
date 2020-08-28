package `fun`.wackloner.marivanna.web

import `fun`.wackloner.marivanna.model.Expression
import `fun`.wackloner.marivanna.repositories.ExpressionRepository
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/translations")
class TranslationController(
        private val expressionRepository: ExpressionRepository
) {
    @GetMapping
    fun getAllTranslations(): ResponseEntity<List<Expression>> {
        val translations = expressionRepository.findAll()
        return ResponseEntity.ok(translations)
    }

    @GetMapping("/{id}")
    fun getOneTranslation(@PathVariable("id") id: String): ResponseEntity<Expression> {
        val translation = expressionRepository.findById(ObjectId(id))
        if (translation.isEmpty)
            return ResponseEntity.notFound().build()
        return ResponseEntity.ok(translation.get())
    }
}
