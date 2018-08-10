package com.sunzhuo.kindle.module.sender.utils

class UrlUtil {
    fun parseUrl(url: String): String {
        //TODO 过滤乱七八糟的网址
        val urlStr = clipUrl(url)
        return urlStr
    }

    private fun clipUrl(url: String): String {
        val startIndex = url.indexOf("http")
        if (startIndex == -1) {
            return ""
        }
        return url.substring(startIndex)
    }
}
