package `fun`.wackloner.marivanna.web

import `fun`.wackloner.marivanna.Translation
import `fun`.wackloner.marivanna.commands.learning
import `fun`.wackloner.marivanna.commands.native
import `fun`.wackloner.marivanna.managers.TranslationRepository

import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TranslationControllerIntegrationTest
@Autowired constructor(
        private val translationRepository: TranslationRepository,
        private val restTemplate: TestRestTemplate
) {
    @LocalServerPort
    protected var port: Int = 0

    private val translationId: ObjectId = ObjectId.get()

    @BeforeEach
    fun setUp() {
        translationRepository.deleteAll()
    }

    private fun getRootUrl(): String? = "http://localhost:$port/translations"

    private fun saveOneTask() = translationRepository.save(Translation(learning("cat"), native("кошка"), 420, translationId))

    @Test
    fun `should return all translations`() {
        saveOneTask()

        val response = restTemplate.getForEntity(
                getRootUrl(),
                List::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(1, response.body?.size)
    }

    @Test
    fun `should return single translation by id`() {
        saveOneTask()

        val response = restTemplate.getForEntity(
                getRootUrl() + "/$translationId",
                Translation::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(translationId, response.body?.id)
    }
}
