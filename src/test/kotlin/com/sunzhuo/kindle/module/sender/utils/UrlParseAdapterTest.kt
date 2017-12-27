package com.sunzhuo.kindle.module.sender.utils

import kotlin.test.assertEquals
import org.junit.Test as test

class UrlParseAdapterTest {
    @test
    fun testClipUrl() {
        val url = "我正在看【阿里巴巴员工: 马云给阿里巴巴的员工开的薪水是多少呢?】，分享给你，一起看吧！ http://ag.mp.uc.cn/article.html?uc_param_str=123"
        assertEquals("http://ag.mp.uc.cn/article.html?uc_param_str=123", UrlUtil().parseUrl(url))
    }
}
