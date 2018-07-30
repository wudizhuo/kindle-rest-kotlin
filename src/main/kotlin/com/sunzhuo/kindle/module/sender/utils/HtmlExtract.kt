package com.sunzhuo.kindle.module.sender.utils

import com.sunzhuo.kindle.common.httpstatus.UrlContentNotFoundException
import net.dankito.readability4j.Article
import net.dankito.readability4j.extended.Readability4JExtended
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.client.LaxRedirectStrategy
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.jsoup.nodes.Element
import org.springframework.boot.system.ApplicationTemp
import java.io.File
import java.net.URI
import java.net.URL
import java.util.regex.Pattern
import javax.imageio.ImageIO

class HtmlExtract {
    val imgTagRegex = """<img.*?src="(http.*?)".*?>"""

    fun getReadabilityHtml(url: String): Article {
        val readability4J = Readability4JExtended(url, fetchUrl(url))
        val article = readability4J.parse()
        if (article.articleContent?.tagName() == "div") {
            val html = Element("html")
            val head = Element("head")
            val headMeta = Element("meta")
            headMeta.attr("charset", "utf-8")
            head.insertChildren(0, headMeta)
            html.insertChildren(0, head)
            html.insertChildren(1, Element("body").insertChildren(0, article.articleContent))
            article.articleContent = html
        }
        return article
    }

    private fun fetchUrl(url: String): String {
        val httpclient = HttpClients.custom()
                .setRedirectStrategy(LaxRedirectStrategy()) // adds HTTP REDIRECT support to GET and POST methods
                .build()
        try {
            val get = HttpGet(URI.create(url)) // we're using GET but it could be via POST as well
            get.addHeader("user-agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
            return httpclient.execute(get) {
                it.entity.content.bufferedReader(Charsets.UTF_8).use { it.readText() }
            }
        } catch (e: Exception) {
            throw IllegalStateException(e)
        } finally {
            IOUtils.closeQuietly(httpclient)
        }
    }

    fun getReadabilityHtmlAndSave2Local(url: String): String {
        //TODO 这里没有错误的话 就可以先返回ok 再继续后台做就可以了，不需要前台等这么久
        //TODO 用子线程 继续做
        val readabilityHtml = getReadabilityHtml(url)
        if (readabilityHtml.content == null) {
            throw UrlContentNotFoundException()
        }
        var pageSource = readabilityHtml.content!!
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
        try {
            val image = ImageIO.read(URL(imgUrl))
            val temp = getTempPath(urlString)
            val file = File(temp.path, "kz" + imgUrl.hashCode() + ".png")
            ImageIO.write(image, "png", file)
            val localImgTag = "<img src=${file.path}>"
            return pageSource.replace(imgTag, localImgTag)
        } catch (e: Exception) {
            println("url--$imgUrl")
            e.printStackTrace()
        }
        return pageSource
    }

    fun getTempPath(url: String): File {
        return ApplicationTemp().getDir("kindle_img" + url.hashCode())
    }
}
