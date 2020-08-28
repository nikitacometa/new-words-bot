package `fun`.wackloner.marivanna.utils

import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.Emojis
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


// TODO: add settings button
// TODO: union command, button, emoji, etc into one entity/mapping (eg BotAction)

fun appendEmoji(text: String, commandName: String): String {
    val emoji = when (commandName) {
            "?dictionary" -> Emojis.RED_BOOK
            "?addTranslation" -> Emojis.PENCIL
            "?menu" -> Emojis.DANCING_GIRL
            "?translate" -> Emojis.flag(Settings.LEARNING_LANGUAGE)
            "?save" -> Emojis.FLOPPY
            "?quiz" -> Emojis.SURFER
            "?remind" -> Emojis.ROCKET
            "?reminders" -> Emojis.CRYSTAL_BALL
        else -> return text
    }
    return "$emoji $text"
}


fun newButton(text: String, callbackData: String): InlineKeyboardButton =
        InlineKeyboardButton(appendEmoji(text, callbackData)).setCallbackData(callbackData)

fun keyboardOf(vararg buttonRows: List<InlineKeyboardButton>): InlineKeyboardMarkup =
        InlineKeyboardMarkup().setKeyboard(buttonRows.toList())


fun mainMenuKeyboard(): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", "?translate"),
                newButton("Add words", "?addTranslation")
        ),
        listOf(
                newButton("Quiz", "?quiz"),
                newButton("Dictionary", "?dictionary")
        ),
        listOf(
                newButton("Remind me", "?remind"),
                newButton("My reminders", "?reminders")
        )
)

// TODO: maybe add button 'save & translate'
fun saveKeyboard(): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Save", "?save"),
                newButton("Translate again", "?dictionary")
        ),
        listOf(
                newButton("Menu", "?menu")
        )
)

fun afterSaveKeyboard(): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", "?translate"),
                newButton("Dictionary", "?dictionary")
        ),
        listOf(
                newButton("Menu", "?menu")
        )
)

fun emptyDictionaryKeyboard(): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", "?translate"),
                newButton("Just add", "?addTranslation")
        ),
        listOf(
                newButton("Menu", "?menu")
        )
)

fun afterAddKeyboard(): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Add more", "?addTranslation"),
                newButton("Dictionary", "?dictionary")
        ),
        listOf(
                newButton("Menu", "?menu")
        )
)

fun afterDictionaryKeyboard(): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", "?translate"),
                newButton("Add translations", "?addTranslation")
        ),
        listOf(
                newButton("Quiz", "?quiz"),
                newButton("Remind me", "?remind")
        )
)


fun oneLineKeyboard(vararg buttons: InlineKeyboardButton): InlineKeyboardMarkup = keyboardOf(buttons.toList())

// TODO: rename
fun menuKeyboard(): InlineKeyboardMarkup = oneLineKeyboard(
        newButton("Dictionary", "?dictionary"),
        newButton("Menu", "?menu")
)

fun retryKeyboard(): InlineKeyboardMarkup = oneLineKeyboard(
        // TODO: implement retryAdd
        newButton("Retry", "?retryAdd"),
        newButton("Menu", "?menu")
)
