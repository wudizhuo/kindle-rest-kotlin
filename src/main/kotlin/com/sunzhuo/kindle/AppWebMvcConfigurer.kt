package com.sunzhuo.kindle

import com.moesif.servlet.MoesifFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter

@Configuration
@EnableWebMvc
class AppWebMvcConfigurer: WebMvcConfigurer{
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("http://www.kindlezhushou.com", "http://kindlezhushou.com")
    }

    @Bean
    fun moesifFilter(): Filter {
        return MoesifFilter("eyJhcHAiOiIxNTA6MjEwIiwidmVyIjoiMi4wIiwib3JnIjoiMjQwOjE4NCIsImlhdCI6MTUzMzYwMDAwMH0.xd2QXMfLVa3ph0fd9ZaQKko_2ZXV8xtmHca7W_QJBAY")
    }
}
