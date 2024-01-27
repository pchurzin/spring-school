package ru.pchurzin.spring.school.data.access.tx

import org.h2.jdbcx.JdbcDataSource
import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.AliasFor
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import javax.sql.DataSource
import kotlin.reflect.KClass

@Transactional
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class KTransactional(
    @get:AliasFor(attribute = "rollbackFor", annotation = Transactional::class)
    val rollbackFor: Array<KClass<out Throwable>> = [Throwable::class]
)

interface Greeter {
    fun greet()
}

class TransactionalGreeter : Greeter {
    @KTransactional
    override fun greet() {
        println("Greetings from transaction")
        throw IOException()
    }
}

@Configuration
@EnableTransactionManagement
class TransactionalConfiguration {
    @Bean
    fun dataSource(): DataSource = JdbcDataSource().apply {
        setURL("jdbc:h2:mem:test_mem")
    }

    @Bean
    fun transactionManager() = JdbcTransactionManager(dataSource())

    @Bean
    fun greeter() = TransactionalGreeter()
}

fun main() {
    val ctx = AnnotationConfigApplicationContext(TransactionalConfiguration::class.java)
    ctx.getBean<Greeter>().greet()
}