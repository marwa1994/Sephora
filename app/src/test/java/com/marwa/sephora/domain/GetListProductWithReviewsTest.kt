package com.marwa.sephora.domain


import com.marwa.sephora.data.mapper.toProduct
import com.marwa.sephora.data.mapper.toReview
import com.marwa.sephora.data.model.BrandDto
import com.marwa.sephora.data.model.ImagesUrlDto
import com.marwa.sephora.data.model.ProductDto
import com.marwa.sephora.data.model.ProductReviewDto
import com.marwa.sephora.data.model.ReviewDto
import com.marwa.sephora.domain.model.ProductsReview
import com.marwa.sephora.domain.usecases.GetListProductWithReviews
import com.marwa.sephora.domain.usecases.GetListProducts
import com.marwa.sephora.domain.usecases.GetListReview
import com.marwa.sephora.domain.usecases.OrderRatingReviewsDown
import com.marwa.sephora.domain.usecases.OrderRatingReviewsUp
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class GetListProductWithReviewsTest {
    @RelaxedMockK
    private lateinit var getListProducts: GetListProducts

    @RelaxedMockK
    private lateinit var getListReview: GetListReview

    @RelaxedMockK
    private lateinit var orderRatingReviewsDown: OrderRatingReviewsDown

    lateinit var getListProductWithReviews: GetListProductWithReviews

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        getListProductWithReviews =
            GetListProductWithReviews(
                getListProducts,
                getListReview,
                orderRatingReviewsDown,
                dispatcher
            )
    }

    @Test
    fun check_that_getListProducts_returns_list_products() = runTest(dispatcher) {
        val listReviews = listOf(
            ProductReviewDto(
                productId = 1461267310,
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
        val listProducts = listOf(
            ProductDto(
                brand = BrandDto(id = "SEPHO", name = "SEPHORA COLLECTION"),
                description = "Craquez pour le Mascara Size Up de Sephora Collection s ultra allongés",
                imagesUrl = ImagesUrlDto(
                    large = "",
                    small = "https://dev.sephora.fr/on/demandware.static/-/Library-Sites-SephoraV2/default/dw521a3f33/brands/institbanner/SEPHO_900_280_institutional_banner_20210927_V2.jpg"
                ),
                isProductSet = false,
                isSpecialBrand = false,
                price = 140.0,
                productId = 1461267310,
                productName = "Size Up - Mascara Volume Extra Large Immédiat"
            )
        )
        val productsReview = listOf(
            ProductsReview(
                product = listProducts[0].toProduct(),
                reviews = listReviews[0].reviews?.toReview(listProducts[0].productId)
            )
        )
        coEvery { getListProducts() } returns listProducts

        coEvery { getListReview() } returns listReviews
        coEvery { orderRatingReviewsDown(productsReview) } returns productsReview

        val result = getListProductWithReviews()
        Assert.assertEquals(result?.get(0)?.product, listProducts[0].toProduct())
        Assert.assertEquals(
            result?.get(0)?.reviews,
            listReviews[0].reviews?.toReview(listProducts[0].productId)
        )

    }
    @Test
    fun check_that_getListReviews_not_called_when_getListProducts_fails() = runTest(dispatcher) {
        coEvery { getListProducts() } throws Throwable("exception")
        assertFailsWith<Throwable> { getListProductWithReviews() }
        coVerify(exactly = 0) { getListReview()  }
    }

    @Test
    fun check_that_getListProductsWithReviews_returns_error_when_two_calls_fails() = runTest(dispatcher) {
        coEvery { getListProducts() } throws Throwable("exception")
        coEvery { getListReview() } throws Throwable("exception")
        assertFailsWith<Throwable> { getListProductWithReviews() }
    }

}