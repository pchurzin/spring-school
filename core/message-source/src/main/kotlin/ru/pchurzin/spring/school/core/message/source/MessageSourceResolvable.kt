package ru.pchurzin.spring.school.core.message.source

import org.springframework.context.MessageSourceResolvable
import org.springframework.context.support.ResourceBundleMessageSource
import java.util.*

fun main() {
    val messageSource = ResourceBundleMessageSource().apply {
        setDefaultEncoding("UTF-8")
        addBasenames("msg")
    }
    val ruLocale = Locale.forLanguageTag("ru")
    val okMessage = messageSource.getMessage(RequestStatus.OK.messageSourceResolvable(), ruLocale)
    val failMessage = messageSource.getMessage(RequestStatus.Fail.messageSourceResolvable(), ruLocale)
    val failMessage2 = messageSource.getMessage(ClassNameMessageSourceResolvable(RequestStatus.Fail), ruLocale)
    val pendingMessage = messageSource.getMessage(RequestStatus.Pending.messageSourceResolvable(), ruLocale)

    println(okMessage)
    println(failMessage)
    println(failMessage2)
    println(pendingMessage)
}

sealed interface RequestStatus {
    data object OK : RequestStatus
    data object Fail: RequestStatus
    data object Pending: RequestStatus
}

fun RequestStatus.messageSourceResolvable(): MessageSourceResolvable = RequestStatusMessageSourceResolvable(this)

private class RequestStatusMessageSourceResolvable(
    private val requestStatus: RequestStatus
) : MessageSourceResolvable {

    override fun getCodes(): Array<String> = when (requestStatus) {
        RequestStatus.OK -> arrayOf("request.status.ok")
        RequestStatus.Fail -> arrayOf("request.status.fail")
        RequestStatus.Pending -> arrayOf("request.status.pending")
    }

    override fun getDefaultMessage(): String = requestStatus.toString()
}

class ClassNameMessageSourceResolvable(
    private val any: Any
): MessageSourceResolvable {
    override fun getCodes(): Array<String> = arrayOf(any::class.java.name)

    override fun getDefaultMessage(): String = any.toString()
}