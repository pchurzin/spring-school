package ru.pchurzin.spring.school.core.container

import org.springframework.beans.factory.getBean
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.beans.factory.support.RootBeanDefinition
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
    beanFactoryContext()
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

fun beanFactoryContext() {
    println("Create context using DefaultListableBeanFactory")
    val factory = DefaultListableBeanFactory()
    factory.registerBeanDefinition("service", RootBeanDefinition(Service::class.java))
    val context = GenericApplicationContext(factory).also { it.refresh() }
    hello(context)
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