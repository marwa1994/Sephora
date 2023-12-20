package com.marwa.sephora.ui.mapper

import com.marwa.sephora.domain.model.Product
import com.marwa.sephora.domain.model.ProductsReview
import com.marwa.sephora.domain.model.Review
import com.marwa.sephora.ui.model.ProductsReviewUiModel

fun ProductsReviewUiModel.toProductsReview() =
    ProductsReview(
        product = Product(
            name = this.product?.name,
            price = this.product?.price?.removeSuffix("€")?.toDouble(),
            imageUrl = this.product?.imageUrl,
            productId = this.product?.productId
        ),
        reviews = reviews?.map {
            Review(
                description = it.description,
                rating = it.rating.toDouble(),
                productId = this.product?.productId
            )
        })




