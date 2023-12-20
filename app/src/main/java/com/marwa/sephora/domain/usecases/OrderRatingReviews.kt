package com.marwa.sephora.domain.usecases

import com.marwa.sephora.di.DefaultDispatcher
import com.marwa.sephora.domain.model.ProductsReview
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRatingReviews @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val orderRatingReviewsDown: OrderRatingReviewsDown,
    private val orderRatingReviewsUp: OrderRatingReviewsUp
) {
    suspend operator fun invoke(
        productsReview: List<ProductsReview>?,
        orderIsUp: Boolean
    ): List<ProductsReview>? =
        withContext(dispatcher) {
            if (orderIsUp) {
                return@withContext orderRatingReviewsUp(productsReview)
            } else {
                return@withContext orderRatingReviewsDown(productsReview)
            }

        }
}