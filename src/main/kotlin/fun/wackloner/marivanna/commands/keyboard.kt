package `fun`.wackloner.marivanna.commands

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup

fun mainMenuKeyboard(dictionaryButton: Boolean = true): InlineKeyboardMarkup {
    val buttonRows = mutableListOf(
            listOf(
                    newButton("Translate", "translate"),
                    newButton("Add translation", "addTranslation")
            ),
            listOf(
                    newButton("Quiz", "quiz"),
                    newButton("Schedule tests", "schedule")
            )
    )
    if (dictionaryButton)
        buttonRows.add(listOf(newButton("Dictionary", "dictionary")))
    return InlineKeyboardMarkup().setKeyboard(buttonRows)
}

fun cancelKeyboard(): InlineKeyboardMarkup {
    val buttonRows = mutableListOf(
            listOf(
                    newButton("Dictionary", "dictionary"),
                    newButton("Cancel", "cancel")
            )
    )
    return InlineKeyboardMarkup().setKeyboard(buttonRows)
}