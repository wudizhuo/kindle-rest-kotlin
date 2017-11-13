package com.sunzhuo.kindle.module.sender.rest

import com.sunzhuo.kindle.common.httpstatus.UrlInvalidException
import com.sunzhuo.kindle.module.sender.utils.HtmlExtract
import com.sunzhuo.kindle.module.sender.utils.UrlUtil
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PreviewController {

    @PostMapping("/preview")
    fun preview(@RequestBody url: String): String {
        var urlStr=url.trim()
        if(urlStr.isEmpty()){
            throw UrlInvalidException()
        }
        urlStr = UrlUtil.parseUrl(urlStr)
        val urlValidator = UrlValidator(arrayOf("http", "https"))
        if(!urlValidator.isValid(urlStr)){
            throw UrlInvalidException()
        }


        var htmlModel = HtmlExtract.getReadabilityHtml(urlStr)
        return urlStr
    }
}
