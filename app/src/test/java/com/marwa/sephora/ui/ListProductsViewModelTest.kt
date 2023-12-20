package com.marwa.sephora.ui

import com.marwa.sephora.domain.model.Product
import com.marwa.sephora.domain.model.ProductsReview
import com.marwa.sephora.domain.model.Review
import com.marwa.sephora.domain.usecases.FilterProducts
import com.marwa.sephora.domain.usecases.GetListProductWithReviews
import com.marwa.sephora.domain.usecases.OrderRatingReviews
import com.marwa.sephora.ui.listproducts.ListProductsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListProductsViewModelTest {

    @RelaxedMockK
    lateinit var filterProducts: FilterProducts

    @RelaxedMockK
    lateinit var getListProductWithReviews: GetListProductWithReviews


    @RelaxedMockK
    lateinit var orderRatingReviews: OrderRatingReviews

    lateinit var viewModel: ListProductsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel =
            ListProductsViewModel(getListProductWithReviews, filterProducts, orderRatingReviews)
    }

    @Test
    fun check_that_getListProductsWithReview_returns_list_products() =
        runTest(mainDispatcherRule.testDispatcher) {
            val productsReview = listOf(
                ProductsReview(
                    product = Product(
                        name = "BBB",
                        price = null,
                        productId = null,
                        imageUrl = null
                    ),
                    reviews = listOf(
                        Review(description = "dd", rating = null, productId = null)
                    )
                ),
                ProductsReview(
                    product = Product(
                        name = "KKK",
                        price = null,
                        productId = null,
                        imageUrl = null
                    ),
                    reviews = listOf(
                        Review(description = "dd", rating = null, productId = null)
                    )
                )
            )
            coEvery { getListProductWithReviews() } returns productsReview

            //WHEN
            viewModel.getListProducts()

            //THEN
            Assert.assertTrue(viewModel.uiState.value is ResultState.Success)
            Assert.assertTrue(
                (viewModel.uiState.value as ResultState.Success)
                    .products?.get(0)?.product?.name.equals("BBB")
            )
        }
}
