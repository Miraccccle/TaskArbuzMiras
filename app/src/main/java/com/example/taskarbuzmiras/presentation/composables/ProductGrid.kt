package com.example.taskarbuzmiras.presentation.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.taskarbuzmiras.R
import com.example.taskarbuzmiras.domain.models.Product
import com.example.taskarbuzmiras.presentation.viewmodels.ProductsViewModel

@Composable
fun ProductGrid(products: List<Product>, productsViewModel: ProductsViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .background(Color(red = 203, green = 241, blue = 205, alpha = 255))
            .padding(bottom = 26.dp),
        content = {
            items(products) { product ->
                val quantity by productsViewModel.getProductQuantity(product).collectAsState()


                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        BorderedImage(imageResUrl = product.imageUrl, size = 200)
                        Text(
                            text = if (product.name.length >= 18) "${product.name.substring(0..17)}..." else product.name,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(8.dp),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Цена: ${product.price}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        if (quantity == 0) {
                            Button(
                                onClick = {
                                    productsViewModel.addToCart(product)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.End),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        red = 83,
                                        green = 201,
                                        blue = 90,
                                        alpha = 255
                                    )
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(text = "Add To Cart")
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Add To Cart")
                            }
                        } else {
                            Row(
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            ) {
                                if (quantity <= 1) {
                                    IconButton(onClick = { productsViewModel.removeFromCart(product) }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Remove One From Cart"
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { productsViewModel.removeOneFromCart(product) }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.remove),
                                            contentDescription = "Cart"
                                        )
                                    }
                                }
                                Text(
                                    text = "$quantity",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                                IconButton(onClick = { productsViewModel.addToCart(product) }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Add One To Cart"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

