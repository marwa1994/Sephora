package com.marwa.sephora.data.repository

import com.marwa.sephora.data.model.BrandDto
import com.marwa.sephora.data.model.ImagesUrlDto
import com.marwa.sephora.data.model.ProductDto
import com.marwa.sephora.data.model.ProductReviewDto
import com.marwa.sephora.data.model.ReviewDto
import com.marwa.sephora.data.service.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class ProductRepositoryTest {
    @RelaxedMockK
    private lateinit var apiService: ApiService
    lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        productRepository = ProductRepository(apiService)
    }

    @Test
    fun check_that_getListProducts_returns_list_products() = runTest {
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
        coEvery { apiService.getListProducts() } returns listProducts

        val result = productRepository.getListProducts()
        Assert.assertEquals(result, listProducts)

    }

    @Test
    fun check_that_getListProducts_returns_error() = runTest {
        coEvery { apiService.getListProducts() } throws Throwable("exception")
        assertFailsWith<Throwable> { productRepository.getListProducts() }
    }

    @Test
    fun check_that_getListReviews_returns_list_reviews() = runTest {
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
        coEvery { apiService.getReviews() } returns listReviews

        val result = productRepository.getListReview()
        Assert.assertEquals(result, listReviews)

    }

    @Test
    fun check_that_getListReviews_returns_error() = runTest {
        coEvery { apiService.getReviews() } throws Throwable("exception")
        assertFailsWith<Throwable> { productRepository.getListReview() }
    }
}