package com.sunzhuo.kindle.module.sender.utils

import com.google.gson.Gson
import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import com.sunzhuo.kindle.module.sender.domain.Article
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.springframework.boot.ApplicationTemp
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.regex.Pattern
import javax.imageio.ImageIO

class HtmlExtract {
    val imgTagRegex = """^<img[\s\S]*?src=["|'](.*?)["|'][\s\S]*?>"""
    val driver: PhantomJSDriver = DriverProvider.getDrive()

    fun getReadabilityHtml(url: String): Article {
        val articleJson: String = getArticleJson(url)
        return Gson().fromJson(articleJson, Article::class.java)
    }

    private fun getArticleJson(url: String): String {
        driver.get(url)
        val js1 = ClassPathResource("readability/JSDOMParser.js").inputStream.bufferedReader().use { it.readText() }
        val js2 = ClassPathResource("readability/Readability.js").inputStream.bufferedReader().use { it.readText() }
        val getArticleAndUpdateDocument = ClassPathResource("getArticle.js").inputStream.bufferedReader().use { it.readText() }
        val js4 = js1 + js2 + getArticleAndUpdateDocument
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
        val m = Pattern.compile(imgTagRegex).matcher(pageSource)
        while (m.find()) {
            pageSource = downloadAndReplace(url, pageSource, m.group(1), m.group(0))
        }
        val temp = getTempPath(url)
        val file = File(temp.path, "kz" + url.hashCode() + ".html")
        file.writeText(pageSource)
        return file.path
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    private fun downloadAndReplace(urlString: String, pageSource: String, imgUrl: String, imgTag: String): String {
        val url = try {
            URL(imgUrl)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            URL("http:" + imgUrl)
        }
        try {
            val image = ImageIO.read(url)
            val temp = getTempPath(urlString)
            val file = File(temp.path, "kz" + imgUrl.hashCode() + ".png")
            ImageIO.write(image, "png", file)
            val localImgTag = "<img src=${file.path}>"
            return pageSource.replace(imgTag, localImgTag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return pageSource
    }

    fun getTempPath(url: String): File {
        return ApplicationTemp().getDir("kindle_img" + url.hashCode())
    }
}
