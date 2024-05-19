package com.example.taskarbuzmiras.domain.repositories

import com.example.taskarbuzmiras.domain.models.CartItemEntity
import com.example.taskarbuzmiras.domain.models.Product
import com.example.taskarbuzmiras.domain.models.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope

interface ProductRepository {

    fun getAllProducts(): Flow<List<Product>>
    suspend fun fetchProductsFromNetwork(apiKey: String): List<Product>
    suspend fun insertProducts(productEntities: List<ProductEntity>)

    fun getAllCartItems(): Flow<List<CartItemEntity>>
    suspend fun upsertCartItem(cartItem: CartItemEntity)
    suspend fun deleteCartItem(cartItem: CartItemEntity)
    suspend fun deleteCartItemsByProductId(productId: Int)
}