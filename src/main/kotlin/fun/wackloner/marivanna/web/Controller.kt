package `fun`.wackloner.marivanna.web

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController {
    @RequestMapping("/")
    fun index(): String {
        return "420"
    }
}