package `fun`.wackloner.marivanna.utils

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.model.Operations
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


// TODO: union command, button, emoji, etc into one entity/mapping (eg BotAction)

fun appendEmoji(text: String, commandName: String, chatId: Long? = null): String {
    val emoji = when (commandName) {
            Operations.DICTIONARY -> Emojis.RED_BOOK
            Operations.ADD_TRANSLATION -> Emojis.PENCIL
            Operations.MENU -> Emojis.DANCING_GIRL
            Operations.TRANSLATE -> "${Emojis.flag(Context.forChat(chatId).sourceLanguage)}${Emojis.flag(Context.forChat(chatId).destLanguage)}"
            Operations.SAVE -> Emojis.FLOPPY
            Operations.QUIZ, Operations.SINGLE_QUIZ -> Emojis.SURFER
            Operations.NOTIFY -> Emojis.ROCKET
            Operations.NOTIFIERS -> Emojis.CRYSTAL_BALL
            Operations.SETTINGS -> Emojis.WRENCH
        else -> return text
    }
    return "$emoji $text"
}


fun newButton(text: String, callbackData: String, chatId: Long? = null): InlineKeyboardButton =
        InlineKeyboardButton(appendEmoji(text, callbackData, chatId)).setCallbackData(callbackData)

fun keyboardOf(vararg buttonRows: List<InlineKeyboardButton>): InlineKeyboardMarkup =
        InlineKeyboardMarkup().setKeyboard(buttonRows.toList())


fun mainMenuKeyboard(chatId: Long): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", Operations.TRANSLATE, chatId),
                newButton("Add words", Operations.ADD_TRANSLATION)
        ),
        listOf(
                // TODO: replace with quiz and implement multi-questioned quizes
                newButton("Quiz", Operations.SINGLE_QUIZ),
                newButton("Dictionary", Operations.DICTIONARY)
        ),
        listOf(
                newButton("Notify me", Operations.NOTIFY),
                newButton("Notifiers", Operations.NOTIFIERS)
        ),
        listOf(
                newButton("Settings", Operations.SETTINGS)
        )
)

fun emptyDictionaryKeyboard(chatId: Long): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", Operations.TRANSLATE, chatId),
                newButton("Just add", Operations.ADD_TRANSLATION)
        ),
        listOf(
                newButton("Menu", Operations.MENU)
        )
)

fun afterDictionaryKeyboard(chatId: Long): InlineKeyboardMarkup = keyboardOf(
        listOf(
                newButton("Translate", Operations.TRANSLATE, chatId),
                newButton("Add translations", Operations.ADD_TRANSLATION)
        ),
        listOf(
                newButton("Menu", Operations.MENU)
        )
)


fun oneLineKeyboard(vararg buttons: InlineKeyboardButton): InlineKeyboardMarkup = keyboardOf(buttons.toList())

fun afterQuizKeyboard(): InlineKeyboardMarkup = oneLineKeyboard(
        newButton("Again", Operations.SINGLE_QUIZ),
        newButton("Menu", Operations.MENU)
)

fun saveKeyboard(chatId: Long): InlineKeyboardMarkup = oneLineKeyboard(
        newButton("${Emojis.flag(Context.forChat(chatId).sourceLanguage)}${Emojis.COUNTER_CLOCKWISE}${Emojis.flag(Context.forChat(chatId).destLanguage)}", Operations.SWAP_LANGUAGES),
        newButton("Save", Operations.SAVE),
        newButton("Menu", Operations.MENU)
)

fun dictionaryOrMenu(): InlineKeyboardMarkup = oneLineKeyboard(
        newButton("Dictionary", Operations.DICTIONARY),
        newButton("Menu", Operations.MENU)
)

fun translateKeyboard(chatId: Long): InlineKeyboardMarkup = oneLineKeyboard(
        newButton("${Emojis.flag(Context.forChat(chatId).sourceLanguage)}${Emojis.COUNTER_CLOCKWISE}${Emojis.flag(Context.forChat(chatId).destLanguage)}", Operations.SWAP_LANGUAGES),
        newButton("Dict", Operations.DICTIONARY),
        newButton("Menu", Operations.MENU)
)
