package com.sunzhuo.kindle

import com.sunzhuo.kindle.module.sender.utils.LoggerReport
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = [(Exception::class)])
    @Throws(Exception::class)
    fun defaultErrorHandler(req: HttpServletRequest, e: Exception): ModelAndView {
        LoggerReport.notifier.report(e)
        throw e
    }
}