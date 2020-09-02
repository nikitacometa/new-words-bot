package `fun`.wackloner.marivanna.bot.commands

import `fun`.wackloner.marivanna.bot.Context
import `fun`.wackloner.marivanna.bot.Settings
import `fun`.wackloner.marivanna.model.Emojis
import `fun`.wackloner.marivanna.model.Expression
import `fun`.wackloner.marivanna.model.Operations
import `fun`.wackloner.marivanna.model.Strings
import `fun`.wackloner.marivanna.utils.keyboardOf
import `fun`.wackloner.marivanna.utils.mainMenuKeyboard
import `fun`.wackloner.marivanna.utils.newButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup

data class Quiz(val options: List<Expression>, val questionWord: String, val rightOption: Int)

fun optionsKeyboard(options: List<Expression>): InlineKeyboardMarkup = keyboardOf(
        // TODO: refactor no const in code
        listOf(
                newButton(Emojis.SPIRAL + options[0].text, Operations.FIRST_OPTION),
                newButton(Emojis.SPIRAL + options[1].text, Operations.SECOND_OPTION)
        ),
        listOf(
                newButton(Emojis.SPIRAL + options[2].text, Operations.THIRD_OPTION),
                newButton(Emojis.SPIRAL + options[3].text, Operations.FOURTH_OPTION)
        ),
        listOf(
                newButton("Cancel", Operations.MENU)
        )
)

fun chooseQuizKeyboard(): InlineKeyboardMarkup = keyboardOf(
        // TODO: refactor no const in code
        listOf(
                newButton("1", "1"),
                newButton("5", "5"),
                newButton("10", "10"),
                newButton("20", "20")
        ),
        listOf(
                newButton("Cancel", Operations.MENU)
        )
)

fun processQuiz(userId: Int, chatId: Long) {
    Context.bot.sendUpdate(chatId, "How many questions do you want?", chooseQuizKeyboard())
}

fun processSingleQuiz(userId: Int, chatId: Long) {
    val allExpressions = Context.expressionRepository.findByUserId(userId)

    if (allExpressions.size < Settings.QUIZ_ANSWERS) {
        Context.bot.sendUpdate(chatId, "Small dictionary :(", mainMenuKeyboard(chatId))
        return
    }

    val shuffled = ArrayList(allExpressions.shuffled())
    val options = shuffled.subList(0, Settings.QUIZ_ANSWERS)
    val rightOption = (0 until Settings.QUIZ_ANSWERS).random()

    val questionWord = options[rightOption].translations[Context.forChat(chatId).nativeLanguage]?.first()?.text!!

    Context.bot.sendUpdate(chatId, "${Emojis.RED_BOOK} <i>$questionWord ${Strings.LONG_DASH} ?</i>", optionsKeyboard(options))
    Context.forChat(chatId).currentQuiz = Quiz(options, questionWord, rightOption)
    Context.forChat(chatId).waitingForAnswer = true
}
