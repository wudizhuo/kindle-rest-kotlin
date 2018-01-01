package com.sunzhuo.kindle.module.sender.utils

import com.google.gson.Gson
import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import com.sunzhuo.kindle.module.sender.domain.Article
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.HttpCommandExecutor
import org.springframework.boot.ApplicationTemp
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.net.URL
import java.util.regex.Pattern
import javax.imageio.ImageIO

class HtmlExtract {
    val driver: PhantomJSDriver
    val regex = """<img[\s\S]*?src\s*=\s*\s*["|'](http.*?)["|'][\s\S]*?>"""

    init {
        val phantomjs = DesiredCapabilities.phantomjs()
        phantomjs.isJavascriptEnabled = true
        phantomjs.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true)
        phantomjs.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, arrayOf("--ssl-protocol=tlsv1", "--ignore-ssl-errors=yes", "--load-images=no"))
        val userAgent = """Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.114 Safari/537.36"""
        phantomjs.setCapability("phantomjs.page.settings.userAgent", userAgent)

        driver = PhantomJSDriver(
                HttpCommandExecutor(URL("http://127.0.0.1:8910")),
                phantomjs)
    }

    fun getReadabilityHtml(url: String): Article {
        val articleJson: String = getArticleJson(url)
        return Gson().fromJson(articleJson, Article::class.java)
    }

    private fun getArticleJson(url: String): String {
        driver.get(url)
        val js1 = ClassPathResource("readability/JSDOMParser.js").inputStream.bufferedReader().use { it.readText() }
        val js2 = ClassPathResource("readability/Readability.js").inputStream.bufferedReader().use { it.readText() }
        val js3 = ClassPathResource("getArticle.js").inputStream.bufferedReader().use { it.readText() }
        val js4 = js1 + js2 + js3
        val articleJson: String?
        try {
            articleJson = driver.executeScript(js4) as String
        } catch (e: WebDriverException) {
            e.printStackTrace()
            throw UrlContentNotFoundException()
        }
        if (articleJson.isEmpty() || articleJson == "null") {
            throw UrlContentNotFoundException()
        }
        return articleJson
    }

    fun getReadabilityHtmlAndSave2Local(url: String): String {
        getArticleJson(url)

        //TODO 这里没有错误的话 就可以先返回ok 再继续后台做就可以了，不需要前台等这么久
        //TODO 用子线程 继续做
        var pageSource = driver.pageSource
        val m = Pattern.compile(regex).matcher(pageSource)
        while (m.find()) {
            pageSource = downloadAndReplace(url, pageSource, m.group(1), m.group(0))
        }
        val temp = getTempPath(url)
        val file = File(temp.path, "kz" + url.hashCode() + ".html")
        file.writeText(pageSource)
        return file.path
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    private fun downloadAndReplace(url: String, pageSource: String, imgUrl: String, imgTag: String): String {
        try {
            val image = ImageIO.read(URL(imgUrl))
            val temp = getTempPath(url)
            val file = File(temp.path, "kz" + imgUrl.hashCode() + ".png")
            ImageIO.write(image, "png", file)
            val localImgTag = "<img src=${file.path}>"
            return pageSource.replace(imgTag, localImgTag)
        } catch (e: ArrayIndexOutOfBoundsException) {
            e.printStackTrace()
        }
        return pageSource
    }

    fun getTempPath(url: String): File {
        return ApplicationTemp().getDir("kindle_img" + url.hashCode())
    }
}
