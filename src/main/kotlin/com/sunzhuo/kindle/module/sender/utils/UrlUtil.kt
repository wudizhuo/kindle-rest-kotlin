package com.sunzhuo.kindle.module.sender.utils

object UrlUtil {
    fun parseUrl(url: String): String {
        val urlStr=clipUrl(url)
        if (url.contains("/^http://t.uc.cn/")){
        }
        return urlStr
    }

    private fun clipUrl(url: String):String {
        return url.substring(url.indexOf("http"))
    }
}
