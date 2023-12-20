package com.marwa.sephora.ui

import com.marwa.sephora.ui.model.ProductsReviewUiModel

sealed class ResultState {
    data object Error : ResultState()
    data object Loading : ResultState()
    data class Success(val products: List<ProductsReviewUiModel>?) : ResultState()
}

