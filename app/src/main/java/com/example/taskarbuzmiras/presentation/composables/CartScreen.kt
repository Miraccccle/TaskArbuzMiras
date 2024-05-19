package com.example.taskarbuzmiras.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import com.example.taskarbuzmiras.presentation.viewmodels.ProductsViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.taskarbuzmiras.presentation.composables.CartItem

@Composable
fun CartScreen(productViewModel: ProductsViewModel) {
    val cartProducts by productViewModel.cartProducts.collectAsState()
    val totalPrice by productViewModel.totalPrice.collectAsState()

    Column {
        Box(modifier = Modifier
            .background(
                Color(
                    red = 83,
                    green = 201,
                    blue = 90,
                    alpha = 255
                )
            )
            .fillMaxWidth()
            .height(80.dp)) {
            Text(
                text = "Cart",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
                ,
                color =  Color.White,
            )
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(red = 203, green = 241, blue = 205, alpha = 255))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartProducts.toList()) { (product, count) ->
                        CartItem(
                            product = product,
                            onRemoveOneFromCart = { productViewModel.removeOneFromCart(it) },
                            onAddToCart = { productViewModel.addToCart(it) },
                            quantity = count
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total: $${String.format("%.2f", totalPrice)}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .align(alignment = Alignment.End)
                        .padding(bottom = 50.dp)
                )
            }
        }
    }

}
