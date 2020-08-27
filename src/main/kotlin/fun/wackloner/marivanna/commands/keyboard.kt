package `fun`.wackloner.marivanna.commands

import `fun`.wackloner.marivanna.Translation
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
                    newButton("Schedule tests", "?schedule")
            )
    )
    if (dictionaryButton)
        buttonRows.add(listOf(newButton("Dictionary", "?dictionary")))
    return InlineKeyboardMarkup().setKeyboard(buttonRows)
}

fun cancelKeyboard(): InlineKeyboardMarkup {
    val buttonRows = mutableListOf(
            listOf(
                    newButton("Dictionary", "?dictionary"),
                    newButton("Cancel", "?cancel")
            )
    )
    return InlineKeyboardMarkup().setKeyboard(buttonRows)
}

fun translateKeyboard(translation: Translation): InlineKeyboardMarkup {
    val saveButtons = translation.translations
            .mapIndexed { i, text -> listOf(newButton("$i) $text", "$i"))}
            .toMutableList()
    saveButtons.add(listOf(
            newButton("Dictionary", "?dictionary"),
            newButton("Cancel", "?cancel")
    ))
    return InlineKeyboardMarkup().setKeyboard(saveButtons)
}
