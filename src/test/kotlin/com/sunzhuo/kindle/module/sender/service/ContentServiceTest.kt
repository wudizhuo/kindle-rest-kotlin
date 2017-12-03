package com.sunzhuo.kindle.module.sender.service

import com.sunzhuo.kindle.module.sender.domain.SendRequest
import org.junit.Test

class ContentServiceTest {

    @Test
    fun testGenMobi() {
        println(ContentService.genMobi("https://www.gatesnotes.com/Health/Digging-Deep-Into-Alzheimers?WT.mc_id=20171119163311_Alzheimers_BG-LI&WT.tsrc=BGLI&linkId=44907215"))
    }

    @Test
    fun testSend() {
        val request=SendRequest("from","to","https://www.gatesnotes.com/Health/Digging-Deep-Into-Alzheimers?WT.mc_id=20171119163311_Alzheimers_BG-LI&WT.tsrc=BGLI&linkId=44907215")
        println(ContentService.send(request))
    }
}
