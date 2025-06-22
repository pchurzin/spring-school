package ru.pchurzin.spring.school.boot.quartz

import org.quartz.*
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.TriggerBuilder.newTrigger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

fun main() {
    runApplication<QuartzApp>()
}

@SpringBootApplication
class QuartzApp {

    @Bean
    fun jobDetail(): JobDetail =
        JobBuilder.newJob(SimpleJob::class.java)
            .withIdentity("simpleJob")
            .storeDurably()
            .build()

    @Bean
    fun simpleJobTrigger(): Trigger =
        newTrigger()
            .forJob(jobDetail())
            .withIdentity("simpleJobTrigger")
            .withSchedule(
                simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever())
            .build()
}

class SimpleJob : Job {
    override fun execute(context: JobExecutionContext) {
        println("Hello from Quartz!")
    }
}