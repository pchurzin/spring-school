package ru.pchurzin.spring.school.integration.distributed.channels

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.integration.channel.QueueChannel
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore
import org.springframework.integration.jdbc.store.channel.PostgresChannelMessageStoreQueryProvider
import org.springframework.integration.store.MessageGroupQueue
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.messaging.MessageChannel
import javax.sql.DataSource

fun main() {
    runApplication<IntegrationApp>()
}

@SpringBootApplication
@EnableConfigurationProperties(InstanceProperties::class)
class IntegrationApp(
    private val instanceProperties: InstanceProperties,
    private val jdbcTemplate: JdbcTemplate,
    private val dataSource: DataSource,
) {

    @Bean
    fun instanceInboundChannel(): MessageChannel = QueueChannel(instanceInboundMessageGroupQueue())

    @Bean
    fun instanceInboundMessageGroupQueue(): MessageGroupQueue =
        MessageGroupQueue(messageStore(), "instanceInboundMessageGroupQueue${instanceId()}")

    @Bean
    fun instanceId(): InstanceId {
        for (instanceId in instanceProperties.minId..instanceProperties.maxId) {
            val locked =
                checkNotNull(jdbcTemplate.queryForObject<Boolean>("SELECT pg_try_advisory_lock(?, ?)", arrayOf(instanceProperties.prefix, instanceId)))
            if(locked) {
                println("Acquired channel lock for instance $instanceId")
                return InstanceId(instanceId)
            }
        }
        error("No free instanceId")
    }

    @Bean
    fun messageStore() = JdbcChannelMessageStore(dataSource).apply {
        setChannelMessageStoreQueryProvider(PostgresChannelMessageStoreQueryProvider())
    }
}

data class InstanceId(val id: Int)

@ConfigurationProperties("app")
data class InstanceProperties(
    val prefix: Int,
    val minId: Int,
    val maxId: Int,
) {
    init {
        require(minId <= maxId) {
            "minId must be less than or equal to maxId"
        }
    }
}