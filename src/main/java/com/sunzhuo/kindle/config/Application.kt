package com.sunzhuo.kindle.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication(exclude = [(DataSourceAutoConfiguration::class)])
class Application {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return AppWebMvcConfigurer()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
