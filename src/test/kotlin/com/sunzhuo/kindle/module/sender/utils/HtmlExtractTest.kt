package com.sunzhuo.kindle.module.sender.utils

import kotlin.test.assertTrue
import org.junit.Test as test

class HtmlExtractTest {
    @test
    fun testGetContentByMozillaLib() {
        val url = "http://www.jianshu.com/p/e815b9e9dd6d?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io"
        assertTrue(HtmlExtract.getReadabilityHtml(url).length > 0)
    }
}
