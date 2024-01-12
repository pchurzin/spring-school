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