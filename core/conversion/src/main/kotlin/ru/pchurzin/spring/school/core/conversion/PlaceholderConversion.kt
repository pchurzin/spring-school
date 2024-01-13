package ru.pchurzin.spring.school.core.conversion

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.support.DefaultConversionService
import java.beans.PropertyEditorSupport

sealed class Greeting(private val value: String) {
    data object Hello : Greeting("HELLO")
    data object Hi : Greeting("HI")
}

// this will be used implicitly (Greeting -> GreetingEditor)
class GreetingEditor : PropertyEditorSupport() {
    override fun setAsText(text: String?) {
        value = when (text?.lowercase()) {
            null -> null
            "hello" -> Greeting.Hello
            "hi" -> Greeting.Hi
            else -> error("Unknown greeting")
        }
    }
}

class StringGreetingConverter : Converter<String, Greeting> {
    override fun convert(source: String): Greeting =
        when (source.lowercase()) {
            "hello" -> Greeting.Hello
            "hi" -> Greeting.Hi
            else -> error("Unknown greeting")
        }
}

class Greeter(private val greeting: Greeting) {
    fun greet() {
        println(greeting)
    }
}

fun ApplicationContext.greet() {
    getBean<Greeter>().greet()
}
fun main() {
    AnnotationConfigApplicationContext(GreeterWithPropertyEditorConfig::class.java).greet()
    AnnotationConfigApplicationContext(GreeterWithConverterConfig::class.java).greet()
}

@Configuration
@PropertySource("classpath:greeter.properties")
class GreeterWithPropertyEditorConfig {
    @Bean
    fun greeter(@Value("\${greeter.greeting}") greeting: Greeting) = Greeter(greeting)
}

@Configuration
@PropertySource("classpath:greeter.properties")
class GreeterWithConverterConfig {
    @Bean
    fun greeter(@Value("\${greeter.greeting}") greeting: Greeting) = Greeter(greeting)

    @Bean
    fun conversionService(): ConversionService = DefaultConversionService().apply {
        addConverter(StringGreetingConverter())
    }
}
