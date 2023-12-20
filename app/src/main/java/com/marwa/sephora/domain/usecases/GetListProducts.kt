package com.marwa.sephora.domain.usecases

import com.marwa.sephora.data.repository.ProductRepository
import com.marwa.sephora.di.IoDispatcher
import com.marwa.sephora.data.utils.NetworkException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetListProducts @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(isCacheNotEnabled: Boolean = true) = withContext(dispatcher) {
        productRepository.getListProducts(isCacheNotEnabled) ?: throw NetworkException.MissingData
    }

}