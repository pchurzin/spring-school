package ru.pchurzin.spring.school.aop.timer

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Timer

fun main() {
    val ctx = AnnotationConfigApplicationContext(Config::class.java)
    val simpleGreeter = ctx.getBean<SimpleGreeter>()
    simpleGreeter.greet("Simple")
    val fastGreeter = ctx.getBean<Greeter>("fastGreeter")
    fastGreeter.greet("Fast")
    val slowGreeter = ctx.getBean<Greeter>("slowGreeter")
    slowGreeter.greet("Slow")
    val sleepingGreeter = ctx.getBean<Greeter>("sleepingGreeter")
    sleepingGreeter.greet("Sleep")
}

@Configuration
@EnableAspectJAutoProxy
class Config {
    // class without interfaces -> cglib proxy will be created
    @Bean
    fun simpleGreeter() = SimpleGreeter()

    @Bean
    fun fastGreeter() = FastGreeter()

    @Bean
    fun slowGreeter() = SlowGreeter()

    @Bean
    fun sleepingGreeter() = SleepingGreeter(Random.nextLong(1000))

    @Bean
    fun timerAspect() = TimerAspect()

}

interface Greeter {
    fun greet(name: String)
}

@Timer
class FastGreeter : Greeter {
    override fun greet(name: String) {
        println("Hello, $name")
    }
}

@Timer
class SlowGreeter : Greeter {
    override fun greet(name: String) {
        TimeUnit.MILLISECONDS.sleep(100)
        println("Hello, $name")
    }
}

class SleepingGreeter(private val sleep: Long) : Greeter {
    @Timer
    override fun greet(name: String) {
        Thread.sleep(sleep)
        println("Hello, $name")
    }
}

// open to allow CGLIB to subclass
open class SimpleGreeter {
    //open to allow CGLIB to proxy the method
    @Timer
    open fun greet(name: String) {
        println("Hello, $name")
    }
}

@Aspect
class TimerAspect {

    @Pointcut("@within(ru.pchurzin.spring.school.aop.timer.Timer)")
    private fun timedType() {}

    @Pointcut("@annotation(ru.pchurzin.spring.school.aop.timer.Timer)")
    private fun timedMethod() {}

    @Pointcut("timedType() || timedMethod()")
    private fun timed() {}

    @Around("timed()")
    fun around(pjp: ProceedingJoinPoint): Any? {
        val start = System.nanoTime()
        val ret = pjp.proceed()
        val stop = System.nanoTime()
        println("Execution took ${stop - start} ns")
        return ret
    }
}