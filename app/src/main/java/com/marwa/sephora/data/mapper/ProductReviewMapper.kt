package com.marwa.sephora.data.mapper

import com.marwa.sephora.data.model.ProductDto
import com.marwa.sephora.data.model.ReviewDto
import com.marwa.sephora.domain.model.Product
import com.marwa.sephora.domain.model.Review

fun ProductDto.toProduct() =
    Product(
        name = this.productName,
        price = this.price,
        productId = this.productId,
        imageUrl = this.imagesUrl?.small
    )

fun List<ReviewDto>.toReview(productId: Int?) =
    this.map { Review(description = it.text, rating = it.rating, productId = productId) }

