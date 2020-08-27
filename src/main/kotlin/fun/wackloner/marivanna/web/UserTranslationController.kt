package `fun`.wackloner.marivanna.web

import `fun`.wackloner.marivanna.UserTranslation
import `fun`.wackloner.marivanna.managers.TranslationRepository
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/translations")
class UserTranslationController(
        private val translationRepository: TranslationRepository
) {
    @GetMapping
    fun getAllTranslations(): ResponseEntity<List<UserTranslation>> {
        val translations = translationRepository.findAll()
        return ResponseEntity.ok(translations)
    }

    @GetMapping("/{id}")
    fun getOneTranslation(@PathVariable("id") id: String): ResponseEntity<UserTranslation> {
        val translation = translationRepository.findById(ObjectId(id))
        if (translation.isEmpty)
            return ResponseEntity.notFound().build()
        return ResponseEntity.ok(translation.get())
    }
}
