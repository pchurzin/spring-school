package ru.pchurzin.spring.school.boot.app

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

fun main(args: Array<String>) {
    val context = runApplication<SpringApplicationConfiguration>(*args)
}

@Configuration
class SpringApplicationConfiguration {

    @Bean
    fun simpleApplicationRunner(ctx: ApplicationContext) = ApplicationRunner {
        val c = ctx
        println(it.optionNames)
    }
}