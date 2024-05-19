package com.example.taskarbuzmiras.data.mappers

import com.example.taskarbuzmiras.domain.models.CartItemEntity
import com.example.taskarbuzmiras.domain.models.Product
import com.example.taskarbuzmiras.domain.models.ProductEntity
import com.example.taskarbuzmiras.domain.models.UnsplashPhoto

fun UnsplashPhoto.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = this.id.hashCode(),
        name = this.description ?: "No name : ${this.id.hashCode()}",
        price = (100..10000).random().toDouble(),
        imageUrl = this.urls.regular
    )
}
fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl
    )
}
fun Product.toCartItemEntity(quantity: Int): CartItemEntity {
    return CartItemEntity(
        productId = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        quantity = quantity
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl
    )
}

fun CartItemEntity.toProduct(): Product {
    return Product(
        id = this.productId,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl
    )
}