package ru.pchruzin.spring.school.boot.observability

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java)
}

@SpringBootApplication
class App {

    @Bean
    fun helloCounter(registry: MeterRegistry): Counter = Counter
        .builder("hello.counter")
        .description("Counter for hello")
        .tags("test", "true")
        .register(registry)

    @Bean
    fun helloTimer(registry: MeterRegistry): Timer = Timer
        .builder("hello.timer")
        .description("Timer for hello")
        .tags("test", "true")
        .register(registry)
}

@RestController
class HelloController(
    private val helloCounter: Counter,
    private val helloTimer: Timer,
) {
    @GetMapping("/hello")
    fun hello(): String {
        helloCounter.increment()
        return helloTimer.recordCallable {
            Thread.sleep(Random.nextLong(10000))
            "Hello World!"
        }!!
    }
}