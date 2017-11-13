package com.sunzhuo.kindle.module.sender.utils

import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

object HtmlExtract {
    fun getReadabilityHtml(url: String): String {
        val driver = RemoteWebDriver(
                URL("http://127.0.0.1:8910"),
                DesiredCapabilities.phantomjs())
        driver.get(url)
//        driver.executeScript("Calculate();")

        return url
    }

    private fun clipUrl(url: String):String {
        return url.substring(url.indexOf("http"))
    }
}
