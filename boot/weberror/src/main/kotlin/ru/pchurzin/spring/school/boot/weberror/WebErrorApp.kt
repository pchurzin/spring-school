package ru.pchurzin.spring.school.boot.weberror

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.stereotype.Component
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
import java.util.*

fun main() {
    runApplication<WebErrorApp>()
}

@SpringBootApplication
class WebErrorApp {

    @Bean
    fun router(handler: HelloHandler) = router {
        GET("/hello", handler)
    }

}

@Component
class HelloHandler : (ServerRequest) -> ServerResponse {
    override fun invoke(p1: ServerRequest): ServerResponse {
        return ServerResponse.badRequest()
            .body(
                ApiResult.Error(HttpStatus.BAD_REQUEST, "Bad request")
                    .updateAndGetBody(null, Locale.getDefault())
            )
    }
}

//@RestController
class WebErrorController {

    @GetMapping("/hello")
    fun hello(): ApiResult {
        return ApiResult.Error(HttpStatus.BAD_REQUEST, "Bad request")
    }
}

sealed class ApiResult {
    abstract val status: String

    data class Success(val message: String) : ApiResult() {
        override val status: String = "OK"
    }

    data class Error(
        val httpStatusCode: HttpStatusCode,
        val message: String
    ) : ApiResult(), ErrorResponse {
        override val status: String = "ERROR"

        override fun getStatusCode(): HttpStatusCode {
            return httpStatusCode
        }

        override fun getBody(): ProblemDetail {
            return ProblemDetail.forStatus(httpStatusCode).also {
                it.detail = message
                it.setProperty("status", "Error")
                it.setProperty("code", httpStatusCode.value().toString())
                it.setProperty("description", message)
            }
        }
    }
}