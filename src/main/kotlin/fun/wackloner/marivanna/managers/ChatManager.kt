package `fun`.wackloner.marivanna.managers

import `fun`.wackloner.marivanna.model.ChatData
import org.springframework.stereotype.Service

@Service
class ChatManager {
    // TODO: use db
    private val chats: MutableMap<Long, ChatData> = HashMap()

    fun getOrCreate(id: Long): ChatData {
        var res = chats[id]
        if (res == null) {
            res = ChatData(id)
            chats[id] = res
        }
        return res
    }
}
