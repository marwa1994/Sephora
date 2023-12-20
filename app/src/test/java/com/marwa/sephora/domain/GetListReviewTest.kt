package com.marwa.sephora.domain

import com.marwa.sephora.data.model.ProductReviewDto
import com.marwa.sephora.data.model.ReviewDto
import com.marwa.sephora.data.repository.ProductRepository
import com.marwa.sephora.domain.usecases.GetListReview
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class GetListReviewTest {
    @RelaxedMockK
    private lateinit var productRepository: ProductRepository
    lateinit var getListReview: GetListReview

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        getListReview = GetListReview(productRepository, dispatcher)
    }

    @Test
    fun check_that_getListProducts_returns_list_products() = runTest(dispatcher) {
        val listReviews = listOf(
            ProductReviewDto(
                productId = 1,
                hide = false,
                reviews = listOf(
                    ReviewDto(
                        rating = 4.4,
                        text = "Ce produit est sublime",
                        name = "name"
                    )
                )
            )
        )
        coEvery { productRepository.getListReview() } returns listReviews

        val result = getListReview()
        Assert.assertEquals(result, listReviews)

    }

    @Test
    fun check_that_getListReviews_returns_error() = runTest {
        coEvery { productRepository.getListReview() } throws Throwable("exception")
        assertFailsWith<Throwable> { getListReview() }
    }
}