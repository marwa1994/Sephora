package com.marwa.sephora.data.model

import com.squareup.moshi.Json

data class ProductReviewDto(
    val hide: Boolean?,
    @Json(name = "product_id")
    val productId: Int?,
    val reviews: List<ReviewDto>?
)

data class ReviewDto(
    val rating: Double?,
    val text: String?,
    val name: String?
)