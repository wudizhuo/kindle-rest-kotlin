package com.sunzhuo.kindle.module.sender.utils

import kotlin.test.assertTrue
import org.junit.Test as test

class HtmlExtractTest {
    @test
    fun testGetContentByMozillaLib() {
        val readabilityHtml = HtmlExtract.getReadabilityHtml("http://www.jianshu.com/p/e815b9e9dd6d?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io")
        println(readabilityHtml.content)
        assertTrue(readabilityHtml.length > 0)
    }

    @test
    fun testWeiXin() {
        val readabilityHtml = HtmlExtract.getReadabilityHtml("https://mp.weixin.qq.com/s/_8_-y7G8NGUj5WeOk3oaMQ")
        println("---3---")
        println(readabilityHtml.content)
        println("---4---")
        assertTrue(readabilityHtml.length > 0)
    }

    @test
    fun testZhihu() {
        val readabilityHtml = HtmlExtract.getReadabilityHtml("https://zhuanlan.zhihu.com/p/22049205")
//        println(readabilityHtml.content)
        assertTrue(readabilityHtml.length > 0)
    }

    @test
    fun testSaveZhihu() {
        HtmlExtract.getReadabilityHtmlAndReplace("https://www.gatesnotes.com/Health/Digging-Deep-Into-Alzheimers?WT.mc_id=20171119163311_Alzheimers_BG-LI&WT.tsrc=BGLI&linkId=44907215")
    }
}
