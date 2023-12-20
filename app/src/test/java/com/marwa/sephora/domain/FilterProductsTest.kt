package com.marwa.sephora.domain

import com.marwa.sephora.data.repository.ProductRepository
import com.marwa.sephora.domain.model.Product
import com.marwa.sephora.domain.model.ProductsReview
import com.marwa.sephora.domain.model.Review
import com.marwa.sephora.domain.usecases.FilterProducts
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FilterProductsTest {
    @RelaxedMockK
    private lateinit var productRepository: ProductRepository
    lateinit var filterProducts: FilterProducts

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        filterProducts = FilterProducts(dispatcher)
    }

    @Test
    fun check_that_getListProducts_returns_list_products() = runTest(dispatcher) {
        val name = "A"
        val productsReview = listOf(
            ProductsReview(
                product = Product(name = "BBB", price = null, productId = null, imageUrl = null),
                reviews = listOf(
                    Review(description = "dd", rating = null, productId = null)
                )
            ),
            ProductsReview(
                product = Product(name = "AAA", price = null, productId = null, imageUrl = null),
                reviews = listOf(
                    Review(description = "dd", rating = null, productId = null)
                )
            )
        )


        val result = filterProducts(productsReview, name)
        Assert.assertTrue(result?.size == 1)
        Assert.assertTrue(result?.firstOrNull { it.product?.name?.contains(name) == true } != null)
    }

    @Test
    fun check_that_getListProducts_returns_listEmpty_when_not_contains_name() =
        runTest(dispatcher) {
            val name = "A"
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


            val result = filterProducts(productsReview, name)
            Assert.assertTrue(result?.size == 0)
            Assert.assertTrue(result?.firstOrNull { it.product?.name?.contains(name) == true } == null)
        }

}