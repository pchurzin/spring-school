package ru.pchurzin.spring.school.core.container

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.beans.factory.getBeansOfType
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.StaticApplicationContext

fun printBeanPostProcessors(c: AbstractApplicationContext) {
    c.refresh()
    val bpp = c.getBeansOfType<BeanPostProcessor>()
    println("-".repeat(10))
    println("${c.javaClass.simpleName} BeanPostProcessor's:\n")
    println(bpp.values.joinToString("\n") { it.javaClass.name })
}

fun main() {
    printBeanPostProcessors(GenericApplicationContext())
    printBeanPostProcessors(ClassPathXmlApplicationContext())
    // with <context:annotation-config/>
    printBeanPostProcessors(ClassPathXmlApplicationContext("context.xml"))
    printBeanPostProcessors(StaticApplicationContext())
    printBeanPostProcessors(AnnotationConfigApplicationContext())
}
