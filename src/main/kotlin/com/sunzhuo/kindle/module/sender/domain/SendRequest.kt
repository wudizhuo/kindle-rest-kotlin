package com.sunzhuo.kindle.module.sender.domain

import com.sunzhuo.kindle.annotation.Data

@Data
data class SendRequest(val from_email: String, val to_email: String, val url: String)