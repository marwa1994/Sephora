package com.marwa.sephora.data.repository

import com.marwa.sephora.data.utils.headerNoCache
import com.marwa.sephora.data.service.ApiService
import javax.inject.Inject


class ProductRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getListProducts(isCacheNotEnabled: Boolean = true) =
        apiService.getListProducts(isCacheNotEnabled.headerNoCache())

    suspend fun getListReview(isCacheNotEnabled: Boolean = true) =
        apiService.getReviews(isCacheNotEnabled.headerNoCache())
}