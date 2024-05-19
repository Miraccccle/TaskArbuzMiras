package com.example.taskarbuzmiras.presentation.composables

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskarbuzmiras.R
import com.example.taskarbuzmiras.data.database.ProductsDatabase
import com.example.taskarbuzmiras.data.api.RetrofitInstance
import com.example.taskarbuzmiras.data.repositories.ProductRepositoryImpl
import com.example.taskarbuzmiras.domain.usecases.GetAllProductsUseCase
import com.example.taskarbuzmiras.presentation.viewmodels.ProductViewModelFactory
import com.example.taskarbuzmiras.presentation.viewmodels.ProductsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val productDao = ProductsDatabase.getDatabase(application).productDao()
            val repository = ProductRepositoryImpl(productDao, RetrofitInstance.api)
            val getAllProductsUseCase = GetAllProductsUseCase(repository)
            val productViewModel: ProductsViewModel = viewModel(factory = ProductViewModelFactory(application, getAllProductsUseCase, repository))
            //productViewModel.fetchProducts()
            MyApp(productsViewModel = productViewModel)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(productsViewModel: ProductsViewModel) {
    val products by productsViewModel.products.observeAsState(emptyList())
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, productViewModel = productsViewModel)
        }
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                ProductGrid(
                    products = products,
                    productsViewModel = productsViewModel
                )
            }
            composable("cart") {
                CartScreen(productViewModel = productsViewModel)
            }
        }
    }
}
