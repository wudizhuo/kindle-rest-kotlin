package com.sunzhuo.kindle.common.httpstatus

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "请填写正确的接收邮箱")
class ToEmailInvalidException(to_email: String) : RuntimeException(to_email)