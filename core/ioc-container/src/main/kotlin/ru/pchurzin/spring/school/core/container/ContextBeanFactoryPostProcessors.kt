package ru.pchurzin.spring.school.core.container

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.getBeansOfType
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.StaticApplicationContext

fun printBeanFactoryPostProcessors(c: AbstractApplicationContext) {
    c.refresh()
    val bfpp = c.getBeansOfType<BeanFactoryPostProcessor>()
    println("-".repeat(10))
    println("${c.javaClass.simpleName} BeanFactoryPostProcessor's:\n")
    println(bfpp.values.joinToString("\n") { it.javaClass.name })
}

fun main() {
    printBeanFactoryPostProcessors(GenericApplicationContext())
    printBeanFactoryPostProcessors(ClassPathXmlApplicationContext())
    // with <context:annotation-config/>
    printBeanFactoryPostProcessors(ClassPathXmlApplicationContext("context.xml"))
    printBeanFactoryPostProcessors(StaticApplicationContext())
    printBeanFactoryPostProcessors(AnnotationConfigApplicationContext())
}