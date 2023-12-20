package com.marwa.sephora.ui.model


data class ProductsReviewUiModel(
    val product: ProductUiModel?,
    val reviews: List<ReviewUiModel>?,
    var isExpanded: Boolean = false,
    val ratings: Float
)

data class ProductUiModel(
    val name: String?,
    val price: String?,
    val imageUrl: String?,
    val productId: Int?
)

data class ReviewUiModel(val description: String?, val rating: Float)

