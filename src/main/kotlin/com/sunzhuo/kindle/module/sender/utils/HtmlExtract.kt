package com.sunzhuo.kindle.module.sender.utils

import com.google.gson.Gson
import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import com.sunzhuo.kindle.module.sender.domain.Article
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.HttpCommandExecutor
import java.io.File
import java.net.URL

object HtmlExtract {
    val driver = PhantomJSDriver(
            HttpCommandExecutor(URL("http://127.0.0.1:8910")),
            DesiredCapabilities.phantomjs())
    val basePath = "src/main/kotlin/com/sunzhuo/kindle/module/sender/"
    fun getReadabilityHtml(url: String): Article {
        val articleJson: String = getArticleJson(url)
        return Gson().fromJson(articleJson, Article::class.java)
    }

    private fun getArticleJson(url: String): String {
        driver.get(url)
        val js1 = File(basePath + "lib/readability/JSDOMParser.js").inputStream().bufferedReader().use { it.readText() }
        val js2 = File(basePath + "lib/readability/Readability.js").inputStream().bufferedReader().use { it.readText() }
        val js3 = File(basePath + "lib/getArticle.js").inputStream().bufferedReader().use { it.readText() }
        val js4 = js1 + js2 + js3
        val articleJson: String = driver.executeScript(js4) as String
        if (articleJson.isEmpty() || articleJson == "null") {
            throw UrlContentNotFoundException()
        }
        return articleJson
    }

    fun getReadabilityHtmlAndReplace(url: String): Article? {
        getArticleJson(url)

        return null
    }
}
