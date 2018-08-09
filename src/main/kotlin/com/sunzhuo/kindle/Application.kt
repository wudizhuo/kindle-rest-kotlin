package com.sunzhuo.kindle

import com.moesif.servlet.MoesifFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter


@SpringBootApplication
class Application {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://www.kindlezhushou.com", "http://kindlezhushou.com")
            }
        }
    }

    @Bean
    fun moesifFilter(): Filter {
        return MoesifFilter("eyJhcHAiOiIxNTA6MjEwIiwidmVyIjoiMi4wIiwib3JnIjoiMjQwOjE4NCIsImlhdCI6MTUzMzYwMDAwMH0.xd2QXMfLVa3ph0fd9ZaQKko_2ZXV8xtmHca7W_QJBAY")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
