package com.marwa.sephora.ui.listproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marwa.sephora.domain.usecases.FilterProducts
import com.marwa.sephora.domain.usecases.GetListProductWithReviews
import com.marwa.sephora.domain.usecases.OrderRatingReviews
import com.marwa.sephora.ui.ResultState
import com.marwa.sephora.ui.mapper.toProductsReview
import com.marwa.sephora.ui.mapper.toProductsReviewUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductsViewModel @Inject constructor(
    val getListProductWithReviews: GetListProductWithReviews,
    val filterProducts: FilterProducts,
    val orderRatingReviews: OrderRatingReviews
) : ViewModel() {
    private val _uiState = MutableStateFlow<ResultState>(ResultState.Loading)
    val uiState: StateFlow<ResultState> = _uiState

    fun getListProducts(isCacheNotEnabled: Boolean = true) {
        viewModelScope.launch {
            _uiState.update { ResultState.Loading }
            try {
                val products = getListProductWithReviews(isCacheNotEnabled)
                _uiState.update {
                    ResultState.Success(products?.map { it.toProductsReviewUiModel() })
                }
            } catch (e: Exception) {
                _uiState.update { ResultState.Error }
            }

        }
    }

    fun filterListProduct(name: String) {
        viewModelScope.launch {
            val productsReview = (_uiState.value as? ResultState.Success)?.products?.map {
                it.toProductsReview()
            }
            val products = filterProducts(products = productsReview, nameProduct = name)
            _uiState.update { ResultState.Success(products?.map { it.toProductsReviewUiModel() }) }
        }

    }

    fun orderRating(isUp: Boolean) {
        viewModelScope.launch {
            val productsReview = (_uiState.value as? ResultState.Success)?.products?.map {
                it.toProductsReview()
            }
            val productsWithOrdredRatings = orderRatingReviews(
                productsReview = productsReview,
                orderIsUp = isUp
            )
            _uiState.update {
                ResultState.Success(productsWithOrdredRatings?.map {
                    it.toProductsReviewUiModel()
                })
            }
        }
    }
}
