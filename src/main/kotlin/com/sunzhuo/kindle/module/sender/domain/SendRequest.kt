package com.sunzhuo.kindle.module.sender.domain

data class SendRequest(val from_email: String, val to_email: String, val url: String)