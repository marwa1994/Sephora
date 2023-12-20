package com.marwa.sephora.data.service

import com.marwa.sephora.data.model.ProductDto
import com.marwa.sephora.data.model.ProductReviewDto
import retrofit2.http.GET
import retrofit2.http.Header

const val HEADER_CACHE_CONTROL = "Cache-Control"
const val HEADER_NO_CACHE = "no-cache"

interface ApiService {
    @GET("testProject/items.json")
    suspend fun getListProducts(
        @Header(HEADER_CACHE_CONTROL) cacheControl: String? = HEADER_NO_CACHE
    ): List<ProductDto>?

    @GET("testProject/reviews.json")
    suspend fun getReviews(
        @Header(HEADER_CACHE_CONTROL) cacheControl: String? = HEADER_NO_CACHE
    ): List<ProductReviewDto>?

}