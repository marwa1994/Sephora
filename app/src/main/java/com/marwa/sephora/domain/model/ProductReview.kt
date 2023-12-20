package com.marwa.sephora.domain.model


data class ProductsReview(
    val product: Product?,
    val reviews: List<Review>?
)

data class Product(
    val name: String?,
    val price: Double?,
    val productId: Int?,
    val imageUrl: String?
)

data class Review(val description: String?, val rating: Double?, val productId: Int?)

