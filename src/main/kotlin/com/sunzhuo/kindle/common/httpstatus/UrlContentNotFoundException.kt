package com.sunzhuo.kindle.common.httpstatus

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "没有找到要显示的内容")
class UrlContentNotFoundException : RuntimeException()
