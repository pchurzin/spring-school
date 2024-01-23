package ru.pchurzin.spring.school.testing.scoped

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.WebRequest
import java.util.UUID

class RequestService(
    private val loginActionProvider: () -> LoginAction,
    private val requestUuidProvider: () -> UUID,
) {
    fun login() {
        println("${requestUuidProvider()}: ${loginActionProvider()}")
    }
}

class LoginAction(private val name: String?, private val password: String?) {
    override fun toString(): String = "$name:$password"
}

@Configuration
class Config(
    /*
    WebRequest is provided by Spring via scoped proxy.
    With this request we create () -> LoginAction which in turn provides LoginAction scoped to request
     */
    private val webRequest: WebRequest,
) {

    @Bean
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    fun loginAction(): LoginAction {
        return LoginAction(webRequest.getParameter("name"), webRequest.getParameter("password"))
    }

    @Bean
    @Scope(WebApplicationContext.SCOPE_REQUEST)
    fun requestUuid(): UUID = UUID.randomUUID()

    @Bean
    fun requestService() = RequestService(::loginAction, ::requestUuid)
}