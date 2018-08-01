package com.sunzhuo.kindle.module.sender.rest

import com.sunzhuo.kindle.common.httpstatus.FromEmailInvalidException
import com.sunzhuo.kindle.common.httpstatus.ToEmailInvalidException
import com.sunzhuo.kindle.module.sender.domain.SendRepository
import com.sunzhuo.kindle.module.sender.domain.SendRequest
import com.sunzhuo.kindle.module.sender.service.ContentService
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class SendController {

    @Autowired
    private lateinit var sendRepository: SendRepository

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    fun send(@RequestBody request: SendRequest) {
        if (!EmailValidator.getInstance().isValid(request.from_email)) {
            throw FromEmailInvalidException()
        }
        if (!EmailValidator.getInstance().isValid(request.to_email)) {
            throw ToEmailInvalidException()
        }

        if (request.to_email.endsWith("@kindle.cn")
                || request.to_email.endsWith("@kindle.com")
                || request.to_email.endsWith("@free.kindle.com")
                || request.to_email.endsWith("@iduokan.com")) {
            ContentService.send(request, sendRepository)
        } else {
            throw ToEmailInvalidException()
        }
    }
}