package com.example.taskarbuzmiras.presentation.viewmodels

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskarbuzmiras.data.mappers.toCartItemEntity
import com.example.taskarbuzmiras.data.mappers.toProduct
import com.example.taskarbuzmiras.data.mappers.toProductEntity
import com.example.taskarbuzmiras.domain.models.Product
import com.example.taskarbuzmiras.domain.repositories.ProductRepository
import com.example.taskarbuzmiras.domain.usecases.GetAllProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel(
    application: Application,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val productRepository: ProductRepository
): AndroidViewModel(application) {
    val products: LiveData<List<Product>> = getAllProductsUseCase.execute().asLiveData()
    private val _cartProducts = MutableStateFlow<Map<Product, Int>>(emptyMap())
    val cartProducts: StateFlow<Map<Product, Int>>  get() = _cartProducts
    val totalPrice: StateFlow<Double> = cartProducts.map { cartItems ->
        cartItems.entries.sumOf { (product, quantity) -> product.price * quantity }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)


    init {
        viewModelScope.launch {
            productRepository.getAllCartItems().collectLatest {
                    cartItems ->
                val updatedCatProducts = cartItems.associate { it.toProduct() to it.quantity }
                _cartProducts.value = updatedCatProducts
            }
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val apiKey = "NE6zPdtTJ8ulvW5nwLMtf4YuiujhXh2_YZU5Wxpa8K0"
                val productsFromNetwork = productRepository.fetchProductsFromNetwork(apiKey)
                val productEntities = productsFromNetwork.map { it.toProductEntity() }
                productRepository.insertProducts(productEntities)
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currentQuantity = _cartProducts.value[product] ?: 0
                val cartItem = product.toCartItemEntity(currentQuantity + 1)
                productRepository.upsertCartItem(cartItem)
            }
        }
    }

    fun removeOneFromCart(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val currentQuantity = _cartProducts.value[product] ?: return@withContext
                if (currentQuantity > 1) {
                    val cartItem = product.toCartItemEntity(currentQuantity - 1)
                    productRepository.upsertCartItem(cartItem)
                } else {
                    productRepository.deleteCartItemsByProductId(product.id)
                }
            }
        }

    }

    fun getProductQuantity(product: Product): StateFlow<Int> {
        return _cartProducts.map { it[product] ?: 0 }
            .stateIn(viewModelScope, SharingStarted.Lazily, 0)
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                productRepository.deleteCartItemsByProductId(product.id)
            }
        }
    }

}