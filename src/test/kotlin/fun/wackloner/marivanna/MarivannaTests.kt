package `fun`.wackloner.marivanna

import javax.ws.rs.core.MediaType

import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders


// TODO: fix test
@SpringBootTest
@AutoConfigureMockMvc
class MarivannaTests {

	@Autowired
	private lateinit var mvc: MockMvc

	@Test
	fun contextLoads() {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("420")));
	}

}
