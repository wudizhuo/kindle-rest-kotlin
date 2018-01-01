package com.sunzhuo.kindle.module.sender.utils

import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.HttpCommandExecutor
import java.net.URL

object DriverProvider {
    val driver: PhantomJSDriver

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

    fun getDrive(): PhantomJSDriver {
        return driver
    }
}