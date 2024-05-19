package com.example.taskarbuzmiras.domain.usecases

import com.example.taskarbuzmiras.domain.models.Product
import com.example.taskarbuzmiras.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetAllProductsUseCase(private val repository: ProductRepository) {
    fun execute(): Flow<List<Product>> {
        return repository.getAllProducts()
    }
}