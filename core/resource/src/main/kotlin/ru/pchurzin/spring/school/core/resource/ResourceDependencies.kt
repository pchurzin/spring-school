package ru.pchurzin.spring.school.core.resource

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.Resource

fun main() {
    val c = AnnotationConfigApplicationContext(Config::class.java)
    c.getBean<ResourceGreeter>().greet()
}

class ResourceGreeter(private val greeting: Resource) {
    fun greet() {
        greeting.inputStream.use {
            println(it.bufferedReader(Charsets.UTF_8).readText())
        }
    }
}

@Configuration
@PropertySource("greeting.properties")
class Config {

    @Bean
    fun greeter(@Value("\${greeting.location}") greeting: Resource) = ResourceGreeter(greeting)

}