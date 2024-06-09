package ru.pchurzin.spring.school.core.container

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

class Greeter(private val greeting: String) {
    fun greet() {
        println(greeting)
    }
}

fun ApplicationContext.greet() {
    val greeter = getBean<Greeter>()
    greeter.greet()
}

fun main() {
    AnnotationConfigApplicationContext(GreeterConfig::class.java).greet()
    AnnotationConfigApplicationContext(NoValueGreeterConfig::class.java).greet()
    try {
        AnnotationConfigApplicationContext(NoValueWithPropertySourcesPlaceholderConfigurer::class.java).greet()
    } catch (e: Exception) {
        println(e)
    }
}

@Configuration
@PropertySource("classpath:greeter.properties")
class GreeterConfig {
    @Bean
    fun greeter(@Value("\${greeter.msg}") greeting: String) = Greeter(greeting)
}

@Configuration
@PropertySource("classpath:greeter.properties")
class NoValueGreeterConfig{
    @Bean
    fun greeter(@Value("\${greeter.msg}") greeting: String) = Greeter(greeting)
}

@Configuration
@PropertySource("classpath:greeter.properties")
class NoValueWithPropertySourcesPlaceholderConfigurer {

    @Bean
    fun greeter(@Value("\${greeter.msg}") greeting: String) = Greeter(greeting)

    companion object {
        @JvmStatic
        @Bean
        fun propertySourcesPlaceholderConfigurer() = PropertySourcesPlaceholderConfigurer()
    }
}