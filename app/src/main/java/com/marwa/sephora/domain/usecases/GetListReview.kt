package com.marwa.sephora.domain.usecases

import com.marwa.sephora.data.repository.ProductRepository
import com.marwa.sephora.di.IoDispatcher
import com.marwa.sephora.data.utils.NetworkException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetListReview @Inject constructor(
    private val productRepository: ProductRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(isCacheNotEnabled: Boolean = true) =
        withContext(dispatcher) {
            productRepository.getListReview(isCacheNotEnabled) ?: throw NetworkException.MissingData
        }
}
