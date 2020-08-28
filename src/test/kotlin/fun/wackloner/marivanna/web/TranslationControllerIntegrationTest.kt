package `fun`.wackloner.marivanna.web

import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.Translation
import `fun`.wackloner.marivanna.repositories.TranslationRepository

import org.bson.types.ObjectId
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension


@AutoConfigureDataMongo
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

    private fun getRootUrl(): String? = "http://localhost:$port/translations"

    @BeforeEach
    fun saveOneTask() {
        translationRepository.save(Translation(420, "cat", "en",
                mapOf(Settings.NATIVE_LANGUAGE to setOf("кошка")), translationId))
    }

    @AfterEach
    fun setUp() {
        translationRepository.deleteAll()
    }

    @Test
    fun `should return all translations`() {
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
        val response = restTemplate.getForEntity(
                getRootUrl() + "/$translationId",
                Translation::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(translationId, response.body?.id)
    }
}
