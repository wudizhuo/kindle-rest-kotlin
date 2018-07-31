package com.sunzhuo.kindle.module.sender.service

import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import com.sunzhuo.kindle.common.httpstatus.UrlInvalidException
import com.sunzhuo.kindle.module.sender.domain.SendRepository
import com.sunzhuo.kindle.module.sender.domain.SendRequest
import com.sunzhuo.kindle.module.sender.domain.UploadRepository
import com.sunzhuo.kindle.module.sender.utils.HtmlExtract
import com.sunzhuo.kindle.module.sender.utils.UrlUtil
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import java.io.File


object ContentService {
    fun getContent(urlStr: String): String {
        val url = checkUrl(urlStr)

        val article = HtmlExtract().getReadabilityHtml(url)
        if (article.content == null) {
            throw UrlContentNotFoundException()
        }
        return article.content!!
    }

    private fun checkUrl(urlStr: String): String {
        var url = urlStr.trim()
        if (url.isEmpty()) {
            throw UrlInvalidException()
        }
        url = UrlUtil().parseUrl(url)
        val urlValidator = UrlValidator(arrayOf("http", "https"))
        if (!urlValidator.isValid(url)) {
            throw UrlInvalidException()
        }
        return url
    }

    fun genMobi(urlStr: String): String {
        val url = checkUrl(urlStr)
        val htmlPath = HtmlExtract().getReadabilityHtmlAndSave2Local(url)
        val process = Runtime.getRuntime().exec("kindlegen $htmlPath")
        process.waitFor()
        process.destroy()
        return htmlPath.replace("html", "mobi")
    }

    fun send(request: SendRequest, sendRepository: SendRepository) {
        try {
            sendEmail(File(genMobi(request.url)), request.to_email, request.from_email)
//            sendRepository.save(request)
        } catch (e: MailException) {
            throw e
        } finally {
            HtmlExtract().getTempPath(request.url).deleteRecursively()
        }
    }

    fun upload(path: String, from_email: String, to_email: String, uploadRepository: UploadRepository) {
        val uploadFile = File(path)
        try {
            sendEmail(uploadFile, to_email, from_email)
//            uploadRepository.save(UploadDomain(uploadFile.name))
        } catch (e: MailException) {
            throw e
        } finally {
            uploadFile.delete()
        }
    }

    @Throws(MailException::class)
    private fun sendEmail(mobiFile: File, to_email: String, from_email: String) {
        if (!mobiFile.exists()) {
            throw UrlContentNotFoundException()
        }
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "localhost"
        mailSender.port = 25

        val message = mailSender.createMimeMessage()
        val messageHelper = MimeMessageHelper(message, true)
        messageHelper.setTo(to_email)
        messageHelper.setFrom(from_email)
        messageHelper.setSubject("convert")
        messageHelper.setText("")
        messageHelper.addAttachment(mobiFile.name, mobiFile)
        mailSender.send(message)
    }
}
