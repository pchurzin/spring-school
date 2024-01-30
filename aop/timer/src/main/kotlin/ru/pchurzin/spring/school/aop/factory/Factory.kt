package ru.pchurzin.spring.school.aop.factory

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Aspect
class SimpleAspect {

    @Pointcut("@within(deprecated)")
    fun deprecatedType(deprecated: Deprecated) {}

    @Before("deprecatedType(deprecated)")
    fun warnDeprecatedType(joinPoint: JoinPoint, deprecated: Deprecated) {
        System.err.println("Used deprecated type: ${joinPoint.target::class.qualifiedName}. Reason = ${deprecated.message}")
    }
}

@Deprecated("To delete")
open class DeprecatedClass {
    open fun hello() {
        println("Hello")
    }
}

@Configuration
class FactoryConfig {
    @Bean
    fun deprecated() : DeprecatedClass {
        val deprecatedClass = DeprecatedClass()
        val factory = AspectJProxyFactory(deprecatedClass).apply {
            addAspect(SimpleAspect::class.java)
        }
        return factory.getProxy()
    }

}
fun main() {
    val ctx = AnnotationConfigApplicationContext(FactoryConfig::class.java)
    val deprecatedClass = ctx.getBean<DeprecatedClass>()
    deprecatedClass.hello()
}