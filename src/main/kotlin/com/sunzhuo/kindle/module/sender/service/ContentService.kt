package com.sunzhuo.kindle.module.sender.service

import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import com.sunzhuo.kindle.common.httpstatus.UrlInvalidException
import com.sunzhuo.kindle.module.sender.domain.Article
import com.sunzhuo.kindle.module.sender.utils.HtmlExtract
import com.sunzhuo.kindle.module.sender.utils.UrlUtil
import org.apache.commons.validator.routines.UrlValidator

object ContentService {
    fun getContent(urlStr: String): String {
        var url = urlStr.trim()
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
        return article.content
    }
}
