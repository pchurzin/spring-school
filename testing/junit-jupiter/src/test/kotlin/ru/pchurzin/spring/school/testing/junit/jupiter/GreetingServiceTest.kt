package ru.pchurzin.spring.school.testing.junit.jupiter

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [Config::class])
abstract class GreetingServiceTest {
    @Autowired
    protected lateinit var applicationContext: ApplicationContext
}

class DefaultGreetingServiceTest : GreetingServiceTest() {

    @Test
    fun defaultTest() {
        val greetingService = applicationContext.getBean<GreetingService>()
        greetingService.greet()
    }
}


@ContextConfiguration(classes = [TestConfig::class])
class TestConfigGreetingProviderGreetingsServiceTest : GreetingServiceTest() {

    @Test
    fun testConfigTest() {
        val greetingService = applicationContext.getBean<GreetingService>()
        greetingService.greet()
    }
}

@Configuration
class TestConfig {
    @Bean
    fun greetingProvider() = DefaultGreetingProvider("Hello from test config")

}

@ContextConfiguration
class NestedTestConfigGreetingServiceTest : GreetingServiceTest() {
    @Configuration
    class Config {
        @Bean
        fun greetingProvider() = DefaultGreetingProvider("Hello from nested test config")
    }

    @Test
    fun nestedConfigTest() {
        val greetingService = applicationContext.getBean<GreetingService>()
        greetingService.greet()
    }
}