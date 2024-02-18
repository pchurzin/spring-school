package ru.pchurzin.spring.school.webmvc.dispatcherservlet.tomcat

import org.apache.catalina.connector.Connector
import org.apache.catalina.startup.Tomcat
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

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
    fun helloController() = HelloController()
}

@Controller
@RequestMapping("/")
class HelloController {
    @ResponseBody
    @GetMapping
    fun hello(): String = "Hello"
}