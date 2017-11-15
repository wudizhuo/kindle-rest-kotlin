package com.sunzhuo.kindle.module.sender.rest

import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import com.sunzhuo.kindle.common.httpstatus.UrlInvalidException
import com.sunzhuo.kindle.module.sender.domain.Article
import com.sunzhuo.kindle.module.sender.domain.RequestUrl
import com.sunzhuo.kindle.module.sender.domain.ResponseContent
import com.sunzhuo.kindle.module.sender.utils.HtmlExtract
import com.sunzhuo.kindle.module.sender.utils.UrlUtil
import org.apache.commons.validator.routines.UrlValidator
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
        var url = requestUrl.url.trim()
        if (url.isEmpty()) {
            throw UrlInvalidException()
        }
        url = UrlUtil.parseUrl(url)
        val urlValidator = UrlValidator(arrayOf("http", "https"))
        if (!urlValidator.isValid(url)) {
            throw UrlInvalidException()
        }

        var article: Article = HtmlExtract.getReadabilityHtml(url)
        if (article.length == 0L) {
            throw UrlContentNotFoundException()
        }
        return ResponseContent(article.content)
    }
}
