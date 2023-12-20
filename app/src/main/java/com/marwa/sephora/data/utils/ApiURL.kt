package com.marwa.sephora.data.utils

import com.marwa.sephora.data.service.HEADER_NO_CACHE

object ApiURL {
    const val BASE_URL = "https://sephoraandroid.github.io/"
    const val TIME_OUT = 40L
    const val MAX_SIZE = 10L * 1024L * 1024L
    const val HTTP_CACHE = "http-cache"
    const val CACHE_CONTROL = "Cache-Control"
}
fun Boolean.headerNoCache() = if (this) HEADER_NO_CACHE else null
