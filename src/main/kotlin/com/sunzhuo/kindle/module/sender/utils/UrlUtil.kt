package com.sunzhuo.kindle.module.sender.utils

class UrlUtil {
    companion object {
        fun parseUrl(url: String): String {
            //TODO 过滤乱七八糟的网址
            val urlStr = clipUrl(url)
            if (url.contains("/^http://t.uc.cn/")) {
            }
            return urlStr
        }

        private fun clipUrl(url: String): String {
            return url.substring(url.indexOf("http"))
        }
    }
}
