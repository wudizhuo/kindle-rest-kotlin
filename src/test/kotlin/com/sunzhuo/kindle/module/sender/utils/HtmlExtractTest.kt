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
    fun testJianShu() {
        val path = HtmlExtract().getReadabilityHtmlAndSave2Local("https://www.jianshu.com/p/cbbf9fc80edc?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io")
        println(path)
        assertNotNull(path)
    }

    @test
    fun testSaveWeixin4ArrayIndexOutOfBoundsException() {
        val path = HtmlExtract().getReadabilityHtmlAndSave2Local("https://mp.weixin.qq.com/s?__biz=MjM5NzA5MDcwMA==&mid=2651017554&idx=4&sn=0ec2311570d9578ebbf544cc50293474&chksm=bd28b5b18a5f3ca7f3e741e16cb5a51324db883a675941607591621ca3913f50e2c14e6cd13e&mpshare=1&scene=1&srcid=0101LG9Yqq3c8XPgeMBeyQgT&key=428377c831859f7c8c19c1b1569081783c5a5c3cb6321073e48f39dec83eb762b4995d145d6fe659e32831dffde33f8fa308534e5c4bbcdd1eb5ee60c9482c182701897ae1e4a2eee8a43e7de6f11cc7&ascene=0&uin=NTIxMDQ0NzM1&devicetype=iMac+MacBookPro13%2C2+OSX+OSX+10.12.3+build(16D32)&version=12020610&nettype=WIFI&lang=zh_CN&fontScale=100&pass_ticket=m7uQPBbgbFbvk9cK%2FZm9kbKx0u0T3Y99RTHkbS3OkKreRAaOyi0qgIG6smGv150%2F")
        println(path)
        assertNotNull(path)
    }

    @test
    fun testPattern() {
        var input = "abc <img alt=\"A growing health crisis: The projected number of people with dementia from 2015 to 2050, millions\" src=\"https://www.gatesnotes.com/-/media/Images/Articles/Health/Digging-Deep-Into-Alzheimers/alzheimers_2017_inline_dementia-graph_800x600_v2.jpg?h=600&amp;w=800&amp;la=en&amp;hash=2506D8E81D10B7E920CDB99C25AADA3A75903064\"> dfg"
        var regex = HtmlExtract().imgTagRegex
        val matcher = Pattern.compile(regex).matcher(input)
        matcher.find()
        assertNotNull(matcher.group(1))
    }

    @test
    fun testPatternForJianshu() {
        val regex = HtmlExtract().imgTagRegex
        val input = """<img data-original-src="//upload-images.jianshu.io/upload_images/1798665-d93eae4c4e6dbd9d.jpg" data-original-width="4032" data-original-height="3024" data-original-format="image/jpeg" data-original-filesize="477338" src="https://upload-images.jianshu.io/upload_images/1798665-d93eae4c4e6dbd9d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/700">"""
        val matcher = Pattern.compile(regex).matcher(input)
        println(matcher.find())
        println(matcher.group(1))
        assertNotNull(matcher.group(1))
    }

    @test
    fun testRegexEndLessUrl(){
        val path = HtmlExtract().getReadabilityHtmlAndSave2Local("https://mp.weixin.qq.com/s/2Bi__FPfSMSli0pw6GtSAQ")
        println(path)
        assertNotNull(path)
    }

    @test
    fun testTempUrl(){
        val path = HtmlExtract().getReadabilityHtmlAndSave2Local("https://mp.weixin.qq.com/s/Sao1fpjdDxMbiMKVtwq1WA")
        println(path)
        assertNotNull(path)
    }
}
