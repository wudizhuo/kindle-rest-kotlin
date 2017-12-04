package com.sunzhuo.kindle.module.sender.service

import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import com.sunzhuo.kindle.common.httpstatus.UrlInvalidException
import com.sunzhuo.kindle.module.sender.domain.Article
import com.sunzhuo.kindle.module.sender.domain.SendRequest
import com.sunzhuo.kindle.module.sender.utils.HtmlExtract
import com.sunzhuo.kindle.module.sender.utils.UrlUtil
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import java.io.File


object ContentService {
    fun getContent(urlStr: String): String {
        val url = checkUrl(urlStr)

        val article: Article = HtmlExtract.getReadabilityHtml(url)
        if (article.length == 0L) {
            throw UrlContentNotFoundException()
        }
        return article.content
    }

    private fun checkUrl(urlStr: String): String {
        var url = urlStr.trim()
        if (url.isEmpty()) {
            throw UrlInvalidException()
        }
        url = UrlUtil.parseUrl(url)
        val urlValidator = UrlValidator(arrayOf("http", "https"))
        if (!urlValidator.isValid(url)) {
            throw UrlInvalidException()
        }
        return url
    }

    fun genMobi(urlStr: String): String {
        val url = checkUrl(urlStr)
        val htmlPath = HtmlExtract.getReadabilityHtmlAndSave2Local(url)
        val process = Runtime.getRuntime().exec("kindlegen " + htmlPath)
        process.waitFor()
        process.destroy()
        return htmlPath.replace("html", "mobi")
    }

    fun send(request: SendRequest) {
        sendEmail(request, genMobi(request.url))
        HtmlExtract.getTempPath(request.url).deleteRecursively()
    }

    private fun sendEmail(request: SendRequest, mobiPath: String) {
        val mobiFile = File(mobiPath)
        if (!mobiFile.exists()) {
            throw UrlContentNotFoundException()
        }
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "localhost"
        mailSender.port = 25

        val message = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message, true)
        messageHelper.setTo(request.to_email)
        messageHelper.setFrom(request.from_email)
        messageHelper.setSubject("convert")
        messageHelper.setText("")
        messageHelper.addAttachment("Kindle ZhuShou", mobiFile)
        mailSender.send(message)
    }
}
