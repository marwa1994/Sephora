package com.marwa.sephora.ui.mapper

import com.marwa.sephora.domain.model.ProductsReview
import com.marwa.sephora.domain.model.Review
import com.marwa.sephora.ui.model.ProductUiModel
import com.marwa.sephora.ui.model.ProductsReviewUiModel
import com.marwa.sephora.ui.model.ReviewUiModel

fun ProductsReview.toProductsReviewUiModel() =
    ProductsReviewUiModel(
        product = ProductUiModel(
            name = this.product?.name,
            price = "${this.product?.price.toString()}€",
            imageUrl = this.product?.imageUrl,
            productId = this.product?.productId,
        ),
        ratings = reviews?.let { getRatings(it) }?.toFloat() ?: 0f,
        reviews = reviews?.map {
            ReviewUiModel(
                description = it.description,
                rating = it.rating?.toFloat()?:0f
            )
        })

fun getRatings(review: List<Review>): Double = review.map { it.rating ?: 0.0 }.average()





