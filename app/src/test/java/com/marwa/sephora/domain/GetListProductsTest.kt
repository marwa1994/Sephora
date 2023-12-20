package com.marwa.sephora.domain

import com.marwa.sephora.data.model.BrandDto
import com.marwa.sephora.data.model.ImagesUrlDto
import com.marwa.sephora.data.model.ProductDto
import com.marwa.sephora.data.repository.ProductRepository
import com.marwa.sephora.domain.usecases.GetListProducts
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

class GetListProductsTest {
    @RelaxedMockK
    private lateinit var productRepository: ProductRepository
    lateinit var getListProducts: GetListProducts

    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        getListProducts = GetListProducts(dispatcher, productRepository)
    }

    @Test
    fun check_that_getListProducts_returns_list_products() = runTest(dispatcher) {
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
        coEvery { productRepository.getListProducts() } returns listProducts

        val result = getListProducts()
        Assert.assertEquals(result, listProducts)

    }

    @Test
    fun check_that_getListProducts_returns_error() = runTest {
        coEvery { productRepository.getListProducts() } throws Throwable("exception")
        assertFailsWith<Throwable> { getListProducts() }
    }
}