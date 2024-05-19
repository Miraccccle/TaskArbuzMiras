package com.example.taskarbuzmiras.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskarbuzmiras.domain.repositories.ProductRepository
import com.example.taskarbuzmiras.domain.usecases.GetAllProductsUseCase
import java.lang.IllegalArgumentException

class ProductViewModelFactory(
    private val application: Application,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val productRepository: ProductRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            return ProductsViewModel(application,getAllProductsUseCase, productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}