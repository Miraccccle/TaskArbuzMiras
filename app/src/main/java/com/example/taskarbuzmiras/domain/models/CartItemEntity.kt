package com.example.taskarbuzmiras.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int
)