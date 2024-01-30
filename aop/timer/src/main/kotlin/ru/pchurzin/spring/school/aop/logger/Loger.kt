package ru.pchurzin.spring.school.aop.logger

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import kotlin.reflect.full.findAnnotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Logger(val level: String = "INFO")

@Logger
open class LoggedClass {

    open fun firstIntParam(anInt: Int, aString: String) {
        println("firstIntParam")
    }

    @Logger(level = "WARN")
    open fun logWithWarn(aString: String) {
        println("logWithWarn")
    }

}
@Aspect
class LoggerAspect {

    @Pointcut("@annotation(logger)")
    private fun loggedMethod(logger: Logger) {}

    @Pointcut("@within(logger)")
    private fun loggedType(logger: Logger) {}

    @Pointcut("loggedMethod(logger) || loggedType(logger)")
    private fun logged(logger: Logger) {}

    @Before("logged(logger) && args(anInt,..)")
    fun beforeInt(logger: Logger, anInt: Int) {
        println("Logged before. Int = $anInt")
    }

    @Before("logged(logger)")
    fun before(jp: JoinPoint, logger: Logger) {
        val methodLogger = jp.target::class.members.firstOrNull { it.name == jp.signature.name }?.findAnnotation<Logger>()
        if (methodLogger != null) {
            println("Before with level ${methodLogger.level}")
        } else {
            jp.target::class.findAnnotation<Logger>()?.let {
                println("Before with level ${it.level}")
            }
        }

    }
}

@Configuration
@EnableAspectJAutoProxy
class Config {
    @Bean
    fun loggerAspect() = LoggerAspect()

    @Bean
    fun loggedClass() = LoggedClass()
}

fun main() {
    val ctx = AnnotationConfigApplicationContext(Config::class.java)
    val loggedClass = ctx.getBean<LoggedClass>()
    loggedClass.firstIntParam(1, "2")
    loggedClass.logWithWarn("a string")
}