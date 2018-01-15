package com.sunzhuo.kindle.module.sender.domain

import com.sunzhuo.kindle.annotation.Data
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository

@Data
data class SendRequest(val from_email: String, val to_email: String, val url: String) {
    @Id
    lateinit var id: String
}

interface SendRepository : MongoRepository<SendRequest, String>

interface UploadRepository : MongoRepository<String, String>