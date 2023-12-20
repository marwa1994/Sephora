package com.marwa.sephora.domain.usecases

import com.marwa.sephora.data.mapper.toProduct
import com.marwa.sephora.data.mapper.toReview
import com.marwa.sephora.di.IoDispatcher
import com.marwa.sephora.domain.model.ProductsReview
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetListProductWithReviews @Inject constructor(
    private val getListProducts: GetListProducts,
    private val getListReview: GetListReview,
    private val orderRatingReviewsDown: OrderRatingReviewsDown,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(isCacheNotEnabled: Boolean = true): List<ProductsReview>? =
        withContext(dispatcher) {
            val listProducts = getListProducts(isCacheNotEnabled)
            val reviews = getListReview(isCacheNotEnabled)
            val listProductsWithReviews = listProducts.map { product ->
                val productReviews = reviews.filter { it.productId == product.productId }
                    .flatMap { p -> p.reviews ?: emptyList() }
                ProductsReview(
                    product = product.toProduct(),
                    reviews = productReviews.toReview(product.productId)
                )
            }
            return@withContext orderRatingReviewsDown(listProductsWithReviews)

        }
}
