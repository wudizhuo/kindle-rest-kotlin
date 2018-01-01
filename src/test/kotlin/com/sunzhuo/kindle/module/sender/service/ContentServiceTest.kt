package com.sunzhuo.kindle.module.sender.service

import org.junit.Test
import kotlin.test.assertNotNull

class ContentServiceTest {

    @Test
    fun testGenMobi() {
        println(ContentService.genMobi("https://www.gatesnotes.com/Health/Digging-Deep-Into-Alzheimers?WT.mc_id=20171119163311_Alzheimers_BG-LI&WT.tsrc=BGLI&linkId=44907215"))
    }

    //TODO 2个问题 1个乱码，一个too large 要改下docker配置的问题  把调试phomes的code 记录下来
    @Test
    fun testSaveXueqiu() {
        val path = ContentService.genMobi("https://xueqiu.com/8563172815/98452396")
        println(path)
        assertNotNull(path)
    }

//    @Test
//    fun testSend() {
//        val request=SendRequest("sunzhuo1228@126.com","372801212@qq.com","https://www.gatesnotes.com/Health/Digging-Deep-Into-Alzheimers?WT.mc_id=20171119163311_Alzheimers_BG-LI&WT.tsrc=BGLI&linkId=44907215")
//        println(ContentService.send(request))
//    }
}
