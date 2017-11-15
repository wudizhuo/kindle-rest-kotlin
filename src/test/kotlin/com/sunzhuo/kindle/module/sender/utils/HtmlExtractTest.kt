package com.sunzhuo.kindle.module.sender.utils

import kotlin.test.assertTrue
import org.junit.Test as test

class HtmlExtractTest {
    @test
    fun testGetContentByMozillaLib() {
        assertTrue(HtmlExtract.getReadabilityHtml("http://www.jianshu.com/p/e815b9e9dd6d?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io").length > 0)
        assertTrue(HtmlExtract.getReadabilityHtml("https://zhuanlan.zhihu.com/p/22049205").length > 0)
    }
}
