package `fun`.wackloner.marivanna.managers

import `fun`.wackloner.marivanna.model.User
import org.springframework.stereotype.Service

@Service
class UserManager {
    // TODO: use db
    private val users: MutableMap<Int, User> = HashMap()

    fun getOrCreate(id: Int, username: String): User {
        var res = users[id]
        if (res == null) {
            res = User(id, username)
            users[id] = res
        }
        return res
    }

    fun get(id: Int): User? = users[id]
}