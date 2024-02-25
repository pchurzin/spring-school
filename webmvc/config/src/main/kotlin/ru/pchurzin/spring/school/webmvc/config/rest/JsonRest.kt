package ru.pchurzin.spring.school.webmvc.config.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.catalina.connector.Connector
import org.apache.catalina.startup.Tomcat
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.*
import org.springframework.web.servlet.i18n.FixedLocaleResolver
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import org.springframework.web.servlet.view.AbstractCachingViewResolver
import org.springframework.web.servlet.view.json.MappingJackson2JsonView
import java.util.*
import kotlin.random.Random

fun main() {
    val ctx = AnnotationConfigWebApplicationContext().also {
        it.register(Config::class.java)
    }
    val dispatcherServlet = DispatcherServlet(ctx)

    val tomcat = Tomcat()
    val connector = Connector()
    connector.port = 8080
    tomcat.service.addConnector(connector)
    val contextPath = ""
    val context = tomcat.addContext(contextPath, "/tmp")
    val servletName = "DispatcherServlet"
    tomcat.addServlet(contextPath, servletName, dispatcherServlet)
    context.addServletMappingDecoded("/", servletName)
    tomcat.start()

    val inReader = System.`in`.bufferedReader()
    while (!inReader.readLine().equals("exit", ignoreCase = true)) {
        Thread.sleep(100)
    }
    tomcat.stop()
}

@Configuration
class Config {
    @Bean
    fun localeResolver(): LocaleContextResolver = FixedLocaleResolver(Locale.US, TimeZone.getTimeZone("UTC"))

    @Bean
    fun handlerMapping(): HandlerMapping = RequestMappingHandlerMapping()

    @Bean
    fun handlerAdapter(): HandlerAdapter = RequestMappingHandlerAdapter()

    @Bean
    fun helloController() = HelloController()

    @Bean
    fun viewResolver(): ViewResolver = object : AbstractCachingViewResolver() {
        override fun loadView(viewName: String, locale: Locale): View = MappingJackson2JsonView(objectMapper())
    }

    @Bean
    fun objectMapper() = jacksonObjectMapper()

    @Bean
    fun handlerExceptionResolver() = MyHandlerExceptionResolver()
}

@Controller
@RequestMapping("/")
class HelloController {
    @GetMapping("hello")
    fun hello(model: Model): String {
        model.addAttribute("result", Random.nextDouble())
        return "Hello"
    }
}

class MyHandlerExceptionResolver : HandlerExceptionResolver {
    override fun resolveException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any?,
        ex: Exception
    ): ModelAndView = ModelAndView("error", mapOf("error" to ex.message))
}