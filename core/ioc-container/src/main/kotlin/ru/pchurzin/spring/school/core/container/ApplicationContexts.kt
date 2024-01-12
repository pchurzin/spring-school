package ru.pchurzin.spring.school.core.container

import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.registerBean
import org.springframework.stereotype.Component

fun main() {
    classpathXmlContext()
    annotationClasspathScanContext()
    annotationConfigContext()
    genericContext()
}

fun hello(c: ApplicationContext) {
    c.getBean<Service>().hello()
}
fun classpathXmlContext() {
    println("Create context using xml")
    hello(ClassPathXmlApplicationContext("context.xml"))
}

fun annotationClasspathScanContext() {
    println("Create context with classpath scanning")
    hello(AnnotationConfigApplicationContext("ru.pchurzin.spring.school.core.container"))
}

fun annotationConfigContext() {
    println("Create context with @Config")
    hello(AnnotationConfigApplicationContext(Config::class.java))
}

fun genericContext() {
    println("Create context using GenericApplicationContext")
    val c = GenericApplicationContext().apply {
        registerBean<Service>()
        refresh()
    }
    hello(c)
}
@Component
class Service {
    fun hello() {
        println("hello")
    }
}

@Configuration
class Config {
    @Bean
    fun service() = Service()

}