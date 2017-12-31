package com.sunzhuo.kindle.module.sender.utils

import com.google.gson.Gson
import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import com.sunzhuo.kindle.module.sender.domain.Article
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.HttpCommandExecutor
import org.springframework.boot.ApplicationTemp
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.net.URL
import java.util.regex.Pattern
import javax.imageio.ImageIO

class HtmlExtract {
    val driver = PhantomJSDriver(
            HttpCommandExecutor(URL("http://127.0.0.1:8910")),
            DesiredCapabilities.phantomjs())

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
        val articleJson: String = driver.executeScript(js4) as String
        if (articleJson.isEmpty() || articleJson == "null") {
            throw UrlContentNotFoundException()
        }
        return articleJson
    }

    fun getReadabilityHtmlAndSave2Local(url: String): String {
        getArticleJson(url)

        //TODO 这里没有错误的话 就可以先返回ok 再继续后台做就可以了，不需要前台等这么久
        //TODO 用子线程 继续做

        var regex = """<img[\s\S]*?src\s*=\s*["|'](.*?)["|'][\s\S]*?>"""
        var pageSource = driver.pageSource

        val m = Pattern.compile(regex).matcher(pageSource)
        while (m.find()) {
            pageSource = downloadAndReplace(url, pageSource, m.group(1))
        }
        var temp = getTempPath(url)
        val file = File(temp.path + "/" + url.hashCode() + ".html")
        file.writeText(pageSource)
        return file.path
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    private fun downloadAndReplace(url: String, pageSource: String, imgUrl: String): String {
        try {
            val image = ImageIO.read(URL(imgUrl))
            val temp = getTempPath(url)
            val file = File(temp.path + "/" + imgUrl.hashCode() + ".png")
            ImageIO.write(image, "png", file)
            return pageSource.replace(imgUrl, file.path)
        } catch (e: ArrayIndexOutOfBoundsException) {
            e.printStackTrace()
        }
        return pageSource
    }

    public fun getTempPath(url: String): File {
        return ApplicationTemp().getDir("kindle_img" + url.hashCode())
    }
}
