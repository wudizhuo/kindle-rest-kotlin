package com.sunzhuo.kindle

import com.moesif.servlet.MoesifFilter
import com.sunzhuo.kindle.module.sender.utils.LoggerReport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter
import javax.servlet.http.HttpServletRequest

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

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = [(Exception::class)])
    @Throws(Exception::class)
    fun defaultErrorHandler(req: HttpServletRequest, e: Exception): ModelAndView {
        LoggerReport.notifier.report(e)
        throw e
    }
}