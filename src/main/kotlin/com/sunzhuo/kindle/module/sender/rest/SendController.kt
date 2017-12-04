package com.sunzhuo.kindle.module.sender.rest

import com.sunzhuo.kindle.common.httpstatus.FromEmailInvalidException
import com.sunzhuo.kindle.common.httpstatus.ToEmailInvalidException
import com.sunzhuo.kindle.module.sender.domain.SendRequest
import com.sunzhuo.kindle.module.sender.service.ContentService
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SendController {

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    fun send(@RequestBody request: SendRequest) {
        if (EmailValidator.getInstance().isValid(request.from_email)) {
            throw FromEmailInvalidException()
        }
        if (EmailValidator.getInstance().isValid(request.to_email)) {
            throw ToEmailInvalidException()
        }
        //TODO 添加修复发附件的功能
        ContentService.send(request)
    }
}