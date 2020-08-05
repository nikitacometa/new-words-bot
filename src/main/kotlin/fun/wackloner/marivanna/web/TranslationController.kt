package `fun`.wackloner.marivanna.web

import `fun`.wackloner.marivanna.managers.Translation
import `fun`.wackloner.marivanna.managers.TranslationRepository
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
class TranslationController(
        private val translationRepository: TranslationRepository
) {
    @GetMapping
    fun getAllTasks(): ResponseEntity<List<Translation>> {
        val tasks = translationRepository.findAll()
        return ResponseEntity.ok(tasks)
    }

    @GetMapping("/{id}")
    fun getOneTask(@PathVariable("id") id: String): ResponseEntity<Translation> {
        val task = translationRepository.findById(ObjectId(id))
        if (task.isEmpty)
            return ResponseEntity.notFound().build()
        return ResponseEntity.ok(task.get())
    }
}