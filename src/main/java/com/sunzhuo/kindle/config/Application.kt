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
    System.getProperties()["server.port"] = "8083"
    System.getProperties()["spring.http.multipart.max-file-size"] = "128MB"
    System.getProperties()["spring.http.multipart.max-request-size"] = "128MB"
    runApplication<Application>(*args)
}
