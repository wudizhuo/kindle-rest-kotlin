package com.sunzhuo.kindle.module.sender.service

import org.junit.Test
import kotlin.test.assertNotNull

class ContentServiceTest {

    @Test
    fun testGenMobi() {
        val path = ContentService.genMobi("https://mp.weixin.qq.com/s?__biz=MjM5NzA5MDcwMA==&mid=2651017554&idx=4&sn=0ec2311570d9578ebbf544cc50293474&chksm=bd28b5b18a5f3ca7f3e741e16cb5a51324db883a675941607591621ca3913f50e2c14e6cd13e&mpshare=1&scene=1&srcid=0101LG9Yqq3c8XPgeMBeyQgT&key=428377c831859f7c8c19c1b1569081783c5a5c3cb6321073e48f39dec83eb762b4995d145d6fe659e32831dffde33f8fa308534e5c4bbcdd1eb5ee60c9482c182701897ae1e4a2eee8a43e7de6f11cc7&ascene=0&uin=NTIxMDQ0NzM1&devicetype=iMac+MacBookPro13%2C2+OSX+OSX+10.12.3+build(16D32)&version=12020610&nettype=WIFI&lang=zh_CN&fontScale=100&pass_ticket=m7uQPBbgbFbvk9cK%2FZm9kbKx0u0T3Y99RTHkbS3OkKreRAaOyi0qgIG6smGv150%2F")
        println(path)
        assertNotNull(path)
    }

    @Test
    fun testSaveXueqiu() {
        val path = ContentService.genMobi("https://xueqiu.com/8563172815/98452396")
        println(path)
        assertNotNull(path)
    }


    @Test
    fun testSaveJianshu() {
        val path = ContentService.genMobi("https://www.jianshu.com/p/cbbf9fc80edc?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io")
        println(path)
        assertNotNull(path)
    }

//    @Test
//    fun testSend() {
//        val request=SendRequest("sunzhuo1228@126.com","372801212@qq.com","https://www.gatesnotes.com/Health/Digging-Deep-Into-Alzheimers?WT.mc_id=20171119163311_Alzheimers_BG-LI&WT.tsrc=BGLI&linkId=44907215")
//        println(ContentService.send(request))
//    }
}
