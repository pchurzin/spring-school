package ru.pchurzin.spring.school.core.conversion

import org.springframework.beans.factory.UnsatisfiedDependencyException
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.registerBean
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.converter.Converter
import org.springframework.core.convert.support.DefaultConversionService
import org.springframework.core.convert.support.GenericConversionService
import kotlin.random.Random

fun main() {
    println("--No conversion service configured --")
    try {
        GenericApplicationContext().apply {
            registerBean<ServiceWithConversion>()
            refresh()
        }
    } catch (e: UnsatisfiedDependencyException) {
        println(e)
    }
    println("--DefaultConversionService--")
    val defaultConversionServiceCtx = GenericApplicationContext().apply {
        registerBean<ServiceWithConversion>()
        registerBean<DefaultConversionService>()
        refresh()
    }
    convert(defaultConversionServiceCtx)

    println("--GenericConversionService with custom converter--")
    val customConverterCtx = GenericApplicationContext().apply {
        registerBean<ServiceWithConversion>()
        registerBean<GenericConversionService>()
        refresh()
    }
    customConverterCtx.getBean<GenericConversionService>().addConverter(IntToStringConverter)
    convert(customConverterCtx)

    println("--GenericConversionService with default converters--")
    val genericConversionServiceWithDefaultConvertersCtx = GenericApplicationContext().apply {
        registerBean<ServiceWithConversion>()
        registerBean<GenericConversionService>()
        refresh()
    }
    DefaultConversionService.addDefaultConverters(genericConversionServiceWithDefaultConvertersCtx.getBean())
    convert(genericConversionServiceWithDefaultConvertersCtx)
}

fun convert(ctx: ApplicationContext) {
    val svc = ctx.getBean<ServiceWithConversion>()
    svc.convertIntegerToString(Random.nextInt())
}

object IntToStringConverter : Converter<Int, String> {
    override fun convert(source: Int): String = source.toString()
}

class ServiceWithConversion(private val conversionService: ConversionService) {
    fun convertIntegerToString(source: Int) {
        println("Converting Int $source to String using ${conversionService::class.java}:")
        println(conversionService.convert(source, String::class.java))
    }
}

