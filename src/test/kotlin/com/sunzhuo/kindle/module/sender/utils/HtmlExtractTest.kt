package com.sunzhuo.kindle.module.sender.utils

import java.util.regex.Pattern
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import org.junit.Test as test

class HtmlExtractTest {
    @test
    fun testGetContentByMozillaLib() {
        val readabilityHtml = HtmlExtract().getReadabilityHtml("http://www.jianshu.com/p/e815b9e9dd6d?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io")
        println(readabilityHtml.content)
        assertTrue(readabilityHtml.length > 0)
    }

    @test
    fun testWeiXin() {
        val readabilityHtml = HtmlExtract().getReadabilityHtml("https://mp.weixin.qq.com/s/_8_-y7G8NGUj5WeOk3oaMQ")
        println(readabilityHtml.content)
        assertTrue(readabilityHtml.length > 0)
    }

    @test
    fun testZhihu() {
        val readabilityHtml = HtmlExtract().getReadabilityHtml("https://zhuanlan.zhihu.com/p/22049205")
        println(readabilityHtml.content)
        assertTrue(readabilityHtml.length > 0)
    }

    @test
    fun testSaveZhihu() {
        assertNotNull(HtmlExtract().getReadabilityHtmlAndSave2Local("https://www.gatesnotes.com/Health/Digging-Deep-Into-Alzheimers?WT.mc_id=20171119163311_Alzheimers_BG-LI&WT.tsrc=BGLI&linkId=44907215"))
    }

    @test
    fun testSaveWeixin4ArrayIndexOutOfBoundsException() {
        assertNotNull(HtmlExtract().getReadabilityHtmlAndSave2Local("http://mp.weixin.qq.com/s/g-sfOdWLIjZtocjHKPYHqA"))
    }

    @test
    fun testPattern() {
        var input = "abc <img alt=\"A growing health crisis: The projected number of people with dementia from 2015 to 2050, millions\" src=\"https://www.gatesnotes.com/-/media/Images/Articles/Health/Digging-Deep-Into-Alzheimers/alzheimers_2017_inline_dementia-graph_800x600_v2.jpg?h=600&amp;w=800&amp;la=en&amp;hash=2506D8E81D10B7E920CDB99C25AADA3A75903064\"> dfg"
        var regex = "<img[\\s|\\S]*?src\\s*=\\s*[\\\"|'](.*?)[\\\"|'][\\s|\\S]*?>"
        val matcher = Pattern.compile(regex).matcher(input)
        matcher.find()
        assertNotNull(matcher.group(1))
    }

}
