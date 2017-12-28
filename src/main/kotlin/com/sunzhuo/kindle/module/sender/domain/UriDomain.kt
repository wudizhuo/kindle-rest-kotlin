package com.sunzhuo.kindle.module.sender.domain

import com.sunzhuo.kindle.annotation.Data

@Data
class UriDomain {
    var spec: String = ""
    var host: String = ""
    var prePath: String = ""
    var scheme: String = ""
    var pathBase: String = ""
}