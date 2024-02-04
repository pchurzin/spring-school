package ru.pchurzin.spring.school.webmvc.dispatcherservlet.annotation

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class WebMvcInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getServletMappings(): Array<String> {
        return arrayOf("/")
    }

    override fun getRootConfigClasses(): Array<Class<*>>? {
        return arrayOf(RootConfiguration::class.java)
    }

    override fun getServletConfigClasses(): Array<Class<*>>? {
        return arrayOf(App1Configuration::class.java)
    }
}

@Configuration
class RootConfiguration

@Configuration
class App1Configuration {

    @Bean
    fun helloController() = HelloController()
}

@RestController
@RequestMapping(path = ["/hello"])
class HelloController {
    @GetMapping
    fun hello() = "Hello!"
}