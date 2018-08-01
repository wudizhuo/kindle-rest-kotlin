package com.sunzhuo.kindle.module.sender.domain

import com.sunzhuo.kindle.annotation.Data
import org.springframework.data.mongodb.repository.MongoRepository

@Data
class UploadDomain(var name: String)

interface UploadRepository : MongoRepository<UploadDomain, String>