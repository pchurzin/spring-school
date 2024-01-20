package ru.pchurzin.spring.school.testing.junit.jupiter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

interface GreetingProvider {
    val greet: String
}

class DefaultGreetingProvider(override val greet: String): GreetingProvider

interface GreetingService {
    fun greet()
}

class DefaultGreetingService(
    private val greetingProvider: GreetingProvider,
) : GreetingService {
    override fun greet() {
        println(greetingProvider.greet)
    }
}

@Configuration
class Config {
    @Bean
    fun greetingProvider() = DefaultGreetingProvider("Hello from main config")

    @Bean
    fun greetingService() = DefaultGreetingService(greetingProvider())

}