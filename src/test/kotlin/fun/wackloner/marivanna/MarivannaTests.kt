package `fun`.wackloner.marivanna

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

import org.junit.jupiter.api.Assertions.assertEquals


@SpringBootTest
class MarivannaTests {

	@Test
	fun contextLoads() {
		assertEquals(420, 199 + 221);
	}

}
