package com.sunzhuo.kindle.module.sender.rest

import com.sunzhuo.kindle.module.sender.domain.PreviewRequest
import com.sunzhuo.kindle.module.sender.domain.PreviewResponse
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
    fun preview(@RequestBody requestUrl: PreviewRequest): PreviewResponse {
        return PreviewResponse(ContentService.getContent(requestUrl.url))
    }
}
