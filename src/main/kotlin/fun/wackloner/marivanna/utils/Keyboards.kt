package `fun`.wackloner.marivanna.utils

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

// TODO: add emojis to buttons

fun newButton(text: String, callbackData: String): InlineKeyboardButton = InlineKeyboardButton(text).setCallbackData(callbackData)

fun keyboardOf(vararg buttonRows: List<InlineKeyboardButton>): InlineKeyboardMarkup =
        InlineKeyboardMarkup().setKeyboard(buttonRows.toList())


fun mainMenuKeyboard(): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", "?translate"),
                newButton("Add translations", "?addTranslation")
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

fun afterSaveKeyboard(): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", "?translate"),
                newButton("Dictionary", "?dictionary")
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

fun saveKeyboard(): InlineKeyboardMarkup = oneLineKeyboard(
        newButton("Save", "?save"),
        newButton("Again", "?translate"),
        newButton("Menu", "?menu")
)
