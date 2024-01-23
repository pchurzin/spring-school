package ru.pchurzin.spring.school.testing.scoped

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig

@SpringJUnitWebConfig(classes = [Config::class])
class ScopedTest {

    @Autowired
    lateinit var request: MockHttpServletRequest

    @Autowired
    lateinit var sut: RequestService

    @Test
    fun testRequestScope() {
        request.setParameter("name", "user1")
        request.setParameter("password", "password1")

        sut.login()
    }
}