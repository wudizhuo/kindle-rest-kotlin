package com.sunzhuo.kindle.module.sender.utils

import org.jsoup.nodes.Element

object ImgUtil{
    fun getImgUrl(url: String, it: Element): String {
        if(url.startsWith("https://zhuanlan.zhihu.com/")){
            return it.absUrl("data-actualsrc")
        }
        if(url.startsWith("https://www.jianshu.com/")){
            return it.absUrl("data-original-src")
        }
        return it.absUrl("src")
    }
}