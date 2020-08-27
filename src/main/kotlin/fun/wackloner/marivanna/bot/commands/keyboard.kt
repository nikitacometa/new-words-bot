package `fun`.wackloner.marivanna.bot.commands

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

// TODO: add emojis to buttons

fun newButton(text: String, callbackData: String): InlineKeyboardButton = InlineKeyboardButton(text).setCallbackData(callbackData)

fun mainMenuKeyboard(dictionaryButton: Boolean = true): InlineKeyboardMarkup {
    val buttonRows = mutableListOf(
            listOf(
                    newButton("Translate", "?translate"),
                    newButton("Add translation", "?addTranslation")
            ),
            listOf(
                    newButton("Quiz", "?quiz"),
                    newButton("Remind me", "?remind")
            )
    )
    if (dictionaryButton)
        buttonRows.add(listOf(newButton("Dictionary", "?dictionary")))
    return InlineKeyboardMarkup().setKeyboard(buttonRows)
}

fun afterSaveKeyboard(): InlineKeyboardMarkup {
    val buttonRows = mutableListOf(
            listOf(
                    newButton("Translate", "?translate"),
                    newButton("Dictionary", "?dictionary")
            ),
            listOf(
                    newButton("Menu", "?menu")
            )
    )
    return InlineKeyboardMarkup().setKeyboard(buttonRows)
}

fun oneLineKeyboard(vararg buttons: InlineKeyboardButton): InlineKeyboardMarkup = InlineKeyboardMarkup().setKeyboard(listOf(buttons.toList()))


// TODO: rename
fun menuKeyboard(): InlineKeyboardMarkup = oneLineKeyboard(
        newButton("Dictionary", "?dictionary"),
        newButton("Menu", "?menu")
)

fun saveKeyboard(): InlineKeyboardMarkup = oneLineKeyboard(
        newButton("Save", "?save"),
        newButton("Again", "?translate"),
        newButton("Menu", "?menu")
)
