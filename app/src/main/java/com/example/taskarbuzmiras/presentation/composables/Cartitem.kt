package com.example.taskarbuzmiras.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.taskarbuzmiras.R
import com.example.taskarbuzmiras.domain.models.Product


@Composable
fun CartItem(
    product: Product,
    onRemoveOneFromCart: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    quantity: Int
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = if (product.name.length >= 61) "${product.name.substring(0..60)}..." else product.name,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                BorderedImage(
                    imageResUrl = product.imageUrl,
                    size = 150
                )
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        if (quantity <= 1) {
                            IconButton(onClick = { onRemoveOneFromCart(product) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove One From Cart"
                                )
                            }
                        } else {
                            IconButton(onClick = { onRemoveOneFromCart(product) }) {
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
                        IconButton(onClick = { onAddToCart(product) }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Remove One From Cart"
                            )
                        }
                    }

                    Text(
                        text = "$${product.price} x $quantity",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.End)
                    )

                }
            }


        }

    }
}