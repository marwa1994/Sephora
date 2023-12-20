package com.marwa.sephora.domain.usecases

import com.marwa.sephora.di.DefaultDispatcher
import com.marwa.sephora.domain.model.ProductsReview
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRatingReviewsUp @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(productsReview: List<ProductsReview>?): List<ProductsReview>? =
        withContext(dispatcher) {
            return@withContext productsReview?.map { product ->
                val sortedReviews = product.reviews?.sortedBy { it.rating }
                product.copy(reviews = sortedReviews)
            }
        }
}
