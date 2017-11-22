package com.sunzhuo.kindle.module.sender.rest

import com.sunzhuo.kindle.module.sender.domain.RequestUrl
import com.sunzhuo.kindle.module.sender.domain.ResponseContent
import com.sunzhuo.kindle.module.sender.service.ContentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class PreviewController {

    @PostMapping("/preview")
    @ResponseStatus(HttpStatus.CREATED)
    fun preview(@RequestBody requestUrl: RequestUrl): ResponseContent {
        return ResponseContent(ContentService.getContent(requestUrl.url))
    }
}
