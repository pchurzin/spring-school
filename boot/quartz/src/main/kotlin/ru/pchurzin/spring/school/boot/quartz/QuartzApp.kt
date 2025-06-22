package ru.pchurzin.spring.school.boot.quartz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main() {
    runApplication<QuartzApp>()
}

@SpringBootApplication
class QuartzApp