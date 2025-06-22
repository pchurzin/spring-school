package ru.pchurzin.spring.school.batch

import org.h2.jdbcx.JdbcDataSource
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.PlatformTransactionManager
import java.util.concurrent.TimeUnit
import javax.sql.DataSource
import kotlin.random.Random

fun main() {
    val context = AnnotationConfigApplicationContext(
        Config::class.java,
        BatchConfig::class.java
    )
}

@Configuration
class Config {

    @Bean
    fun dataSource(): DataSource = JdbcDataSource().apply {
        setURL("jdbc:h2:mem:test_mem;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
    }

    @Bean
    fun transactionManager() = JdbcTransactionManager(dataSource())

    @Bean
    fun dataSourceInitializer(): DataSourceInitializer =
        DataSourceInitializer().apply {
            setDataSource(dataSource())
            setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("org/springframework/batch/core/schema-h2.sql")))
        }
}

@Configuration
@EnableBatchProcessing
@EnableScheduling
class BatchConfig(
    private val jobRepository: JobRepository,
    private val jobLauncher: JobLauncher,
    private val transactionManager: PlatformTransactionManager,
) {

    @Scheduled(fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
    fun scheduledJob() {
        val jobParameters = JobParametersBuilder()
            .addLong("tick", System.currentTimeMillis() % 10)
            .toJobParameters()
        val jobExecution = jobRepository.getLastJobExecution("batchJob", jobParameters)
        if (jobExecution == null || jobExecution.exitStatus !in listOf(ExitStatus.COMPLETED, ExitStatus.EXECUTING)) {
            jobLauncher.run(batchJob(), jobParameters)
        } else {
            println("Skipped job execution")
        }
    }

    @Bean
    fun batchJob(): Job =
        JobBuilder("batchJob", jobRepository)
            .start(step1())
            .build()

    @Bean
    fun step1(): Step =
        StepBuilder("step1", jobRepository)
            .chunk<String, String>(2, transactionManager)
            .reader(itemReader())
            .writer(itemWriter())
            .build()

    @Bean
    fun itemReader(): ItemReader<String> = ItemReader {
        if (Random.nextBoolean()) {
            (0..10)
                .map { "Hello world".random() }
                .joinToString("")
        } else {
            null
        }
    }

    @Bean
    fun itemWriter(): ItemWriter<String> = ItemWriter {
        it.forEach(::println)
    }
}
