package com.sunzhuo.kindle.module.sender.utils

class ContentAdapter {

    fun adapt(url: String, pageSource: String): String {
        var result:String = pageSource
        result = adaptJianShu(url, pageSource)
        return result
    }

    private fun adaptJianShu(url: String, pageSource: String): String {
        val pattern = Regex("""http|https://www.jianshu.com/""")
        if(url.matches(pattern)){

        }
        return pageSource
    }
}