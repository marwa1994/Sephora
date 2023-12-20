package com.marwa.sephora.data.model

import com.squareup.moshi.Json


data class ProductDto(
    @Json(name = "c_brand")
    val brand: BrandDto?,
    val description: String?,
    @Json(name = "images_url")
    val imagesUrl: ImagesUrlDto?,
    @Json(name = "is_productSet")
    val isProductSet: Boolean?,
    @Json(name = "is_special_brand")
    val isSpecialBrand: Boolean?,
    val price: Double?,
    @Json(name = "product_id")
    val productId: Int?,
    @Json(name = "product_name")
    val productName: String?
)

data class ImagesUrlDto(
    val large: String?,
    val small: String?
)

data class BrandDto(
    val id: String?,
    val name: String?
)