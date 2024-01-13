# Ioc Container
## [ApplicationContexts.kt](src/main/kotlin/ru/pchurzin/spring/school/core/container/ApplicationContexts.kt)
Demonstrate how to create different ApplicationContext specializations:
- using xml from classpath
- using @ComponentScan
- using @Configuration classes
- using BeanFactory API

## [ContextBeanFactoryPostProcessors.kt](src/main/kotlin/ru/pchurzin/spring/school/core/container/ContextBeanFactoryPostProcessors.kt)
Shows which BeanFactoryPostProcessor's registered with ApplicationContext.

There are no BFPP by default excluding AnnotationConfigApplicationContext and
XmlApplicationContext with `<context:annotation-config/>` applied. These contexts
register ConfigurationClassPostProcessor and EventListenerMethodProcessor.

## [ContextBeanPostProcessors.kt](src/main/kotlin/ru/pchurzin/spring/school/core/container/ContextBeanPostProcessors.kt)
Show which `BeanPostProcessor`s are registered with ApplicationContext.
By default, `AnnotationConfigApplicationContext` registers `AutowiredAnnotationBeanPostProcessor`.
So does and XmlApplicationContext if there is a `<context:annotation-config/>` applied.
If there is a jakarta.annotation-api on classpath the CommonAnnotationBeanPostProcessor is registered.
If there are a jakarta.persistence-api and spring-orm on classpath the PersistenceAnnotationBeanPostProcessor is registered.