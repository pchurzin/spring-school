package ru.pchurzin.spring.school.core.message.source

import org.springframework.context.MessageSource
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.context.support.StaticMessageSource
import java.util.*

fun main() {
    staticMessageSource()
    resourceBundleMessageSource()
    applicationContextMessageSource()
}

fun staticMessageSource() {
    val ms = StaticMessageSource()
    ms.addMessage("message.color", Locale.UK, "colour")
    val parentMs = StaticMessageSource()
    ms.parentMessageSource = parentMs
    parentMs.addMessage("message.color", Locale.US, "color")

    println(ms.getMessage("message.color", null, Locale.UK))
    // get message from parent message source
    println(ms.getMessage("message.color", null, Locale.US))
}

fun resourceBundleMessageSource() {
    val ms = ResourceBundleMessageSource()
    ms.addBasenames("message")

    println(ms.getMessage("message.test", null, Locale.forLanguageTag("ru")))
    println(ms.getMessage("message.test", null, Locale.UK))
    println(ms.getMessage("message.test", null, Locale.US))
}

fun applicationContextMessageSource() {
    val noMessageSourceContext = AnnotationConfigApplicationContext().also { it.refresh() }
    try {
        println(noMessageSourceContext.getMessage("message.test", null, Locale.UK))
    } catch (e: Exception) {
        println(e)
    }
    val messageSourceContext = AnnotationConfigApplicationContext(MessageSourcesConfig::class.java)
    println(messageSourceContext.getMessage("message.test", null, Locale.UK))

    val childContext = GenericApplicationContext().also {
        it.parent = messageSourceContext
        it.refresh()
    }
    println(childContext.getMessage("message.test", null, Locale.US))
}

@Configuration
class MessageSourcesConfig {
    @Bean("messageSource")
    fun myMessageSource(): MessageSource = ResourceBundleMessageSource().also { it.addBasenames("message") }
}