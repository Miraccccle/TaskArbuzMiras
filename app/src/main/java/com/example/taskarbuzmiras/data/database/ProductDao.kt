package com.example.taskarbuzmiras.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.taskarbuzmiras.domain.models.CartItemEntity
import com.example.taskarbuzmiras.domain.models.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAllProductsOrderedByName(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    // Update and Insert
    @Upsert
    suspend fun upsertProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertCartItem(cartItem: CartItemEntity)

    @Delete
    fun deleteCartItem(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    fun deleteCartItemsByProductId(productId: Int)

}