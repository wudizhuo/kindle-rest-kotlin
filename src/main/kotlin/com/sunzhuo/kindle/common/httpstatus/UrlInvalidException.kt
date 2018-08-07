package com.sunzhuo.kindle.common.httpstatus

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "请填写正确的网址")
class UrlInvalidException(url: String) : RuntimeException(url)