package com.sunzhuo.kindle.module.sender.utils

import org.nibor.autolink.LinkExtractor
import org.nibor.autolink.LinkType
import java.util.*

class UrlUtil {
    fun parseUrl(url: String): String {
        val linkExtractor = LinkExtractor.builder()
                .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW))
                .build()
        val link = linkExtractor.extractLinks(url).iterator().next()
        return url.substring(link.beginIndex, link.endIndex)
    }
}
