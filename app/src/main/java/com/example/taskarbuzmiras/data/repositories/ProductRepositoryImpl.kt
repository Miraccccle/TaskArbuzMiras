package com.example.taskarbuzmiras.data.repositories

import com.example.taskarbuzmiras.data.api.UnsplashApi
import com.example.taskarbuzmiras.data.database.ProductDao
import com.example.taskarbuzmiras.data.mappers.toProduct
import com.example.taskarbuzmiras.data.mappers.toProductEntity
import com.example.taskarbuzmiras.domain.models.Product
import com.example.taskarbuzmiras.domain.models.ProductEntity
import com.example.taskarbuzmiras.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.map
import com.example.taskarbuzmiras.domain.models.CartItemEntity
import kotlinx.coroutines.flow.Flow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val api: UnsplashApi
) : ProductRepository {
    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProductsOrderedByName().map { entities ->
            entities.map {
                it.toProduct()
            }
        }
    }

    override suspend fun fetchProductsFromNetwork(apiKey: String): List<Product> {
        return withContext(Dispatchers.IO) {
            val photos = api.getPhotos(apiKey, 1, 30)
            photos.map {
                it.toProductEntity().toProduct()
            }
        }
    }

    override suspend fun insertProducts(productEntities: List<ProductEntity>) {
        withContext(Dispatchers.IO) {
            productDao.insertAll(productEntities)
        }
    }

    override fun getAllCartItems(): Flow<List<CartItemEntity>> {
        return productDao.getAllCartItems()
    }

    override suspend fun upsertCartItem(cartItem: CartItemEntity) {
        withContext(Dispatchers.IO) {
            productDao.upsertCartItem(cartItem)
        }
    }

    override suspend fun deleteCartItem(cartItem: CartItemEntity) {
        productDao.deleteCartItem(cartItem)
    }

    override suspend fun deleteCartItemsByProductId(productId: Int) {
        productDao.deleteCartItemsByProductId(productId)
    }
}