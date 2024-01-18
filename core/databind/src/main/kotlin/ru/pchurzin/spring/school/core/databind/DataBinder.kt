package ru.pchurzin.spring.school.core.databind

import org.springframework.context.MessageSource
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.core.ResolvableType
import org.springframework.validation.DataBinder
import java.util.*

fun main() {
    constructorBinding()
    constructorBindingWithErrors()
}

fun constructorBinding() {
    val binder = DataBinder(null, "user").also {
        it.targetType = ResolvableType.forClass(User::class.java)
    }

    binder.construct(MapValueResolver(mapOf("firstName" to "Ivan", "lastName" to "Ivanov", "age" to 10)))
    assert(!binder.bindingResult.hasErrors())
    val user = binder.target as User
    assert(user.firstName == "Ivan")
    assert(user.lastName == "Ivanov")
    assert(user.age == 10)
    println(user)
}

/*
bindingResult.allErrors is a list of ObjectError which is MessageSourceResolvable.
This in turn we can resolve with MessageSource
 */
fun constructorBindingWithErrors() {
    val binder = DataBinder(null, "user").also {
        it.targetType = ResolvableType.forClass(User::class.java)
    }

    binder.construct(MapValueResolver(mapOf("firstName" to "Ivan", "lastName" to "Ivanov", "age" to "a")))
    assert(binder.bindingResult.hasErrors())
    val messageSource: MessageSource = ResourceBundleMessageSource().also { it.addBasenames("messages") }
    binder.bindingResult.allErrors.forEach { e ->
        val m = e?.codes?.firstNotNullOf { messageSource.getMessage(it, null, null, Locale.US) }
        println(m)
    }
}
class MapValueResolver(private val map: Map<String, Any>) : DataBinder.ValueResolver {
    override fun resolveValue(name: String, type: Class<*>): Any? {
        return map[name]
    }

    override fun getNames(): MutableSet<String> {
        return map.keys.toMutableSet()
    }
}

data class User(val firstName: String, val lastName: String, val age: Int)