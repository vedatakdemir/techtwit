package com.techtwit.demo.bot

import com.techtwit.demo.service.TechTwitService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.api.objects.Message

@Service
class NormalAnswerPreparer(private val techTwitService: TechTwitService) : AnswerPreparer(techTwitService) {

    companion object {
        private const val START_COMMAND = "/start"
        private const val NEW_SOURCE_COMMAND = "/new"

        private val START_EMOJI = String(Character.toChars(Integer.parseInt("1F60A", 16))) // smiling face
    }

    override fun getAnswer(message: Message): String {
        val messageStartsWith: (String) -> Boolean = { message.text.startsWith(it) }

        return when {
            messageStartsWith(START_COMMAND) -> START_EMOJI
            messageStartsWith(NEW_SOURCE_COMMAND) -> techTwitService.getRandomTechTwit().source
            messageStartsWith(RepliedAnswerPreparer.READ_COMMAND) -> "You need to reply one of my messages to use this command."
            else -> DEFAULT_ANSWER
        }
    }
}