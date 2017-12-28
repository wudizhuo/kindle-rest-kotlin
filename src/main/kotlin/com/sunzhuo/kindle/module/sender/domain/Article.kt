package com.sunzhuo.kindle.module.sender.domain

import com.sunzhuo.kindle.annotation.Data

@Data
class Article {
    var uri: UriDomain? = null
    var title: String = ""
    var content: String = ""
    var length: Long = 0
    var excerpt: String = ""
    var byline: String = ""
    var dir: String = ""
}