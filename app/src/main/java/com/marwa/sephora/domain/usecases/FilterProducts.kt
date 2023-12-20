package com.marwa.sephora.domain.usecases

import com.marwa.sephora.di.DefaultDispatcher
import com.marwa.sephora.domain.model.ProductsReview
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterProducts @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(products: List<ProductsReview>?, nameProduct: String) =
        withContext(dispatcher) {
            products?.filter {
                it.product?.name?.lowercase()?.startsWith(nameProduct.lowercase()) == true
            }
        }
}