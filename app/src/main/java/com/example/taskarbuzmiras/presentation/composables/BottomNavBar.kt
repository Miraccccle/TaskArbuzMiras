package com.example.taskarbuzmiras.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.taskarbuzmiras.domain.models.BottomNavItem
import com.example.taskarbuzmiras.presentation.viewmodels.ProductsViewModel

@Composable
fun BottomNavBar(navController: NavController, productViewModel: ProductsViewModel) {
    val items = listOf(
        BottomNavItem("home", Icons.Default.Home, "Home"),
        BottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")
    )

    val cartProducts by productViewModel.cartProducts.collectAsState()
    val uniqueCartItemCount = cartProducts.size

    BottomNavigation(
        backgroundColor = Color(
            red = 83,
            green = 201,
            blue = 90,
            alpha = 255
        ),
        contentColor = Color.White,  // Цвет для невыбранных элементов
        elevation = 8.dp,
        modifier = Modifier.wrapContentSize(Alignment.BottomCenter)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                label = { Text(item.label, color = Color.White) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (item.route == "cart") {
                        Box {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                            if (uniqueCartItemCount > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(Color.Red, shape = MaterialTheme.shapes.small)
                                        .align(Alignment.TopEnd)
                                ) {
                                    Text(
                                        text = uniqueCartItemCount.toString(),
                                        color = Color.White,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = Color.White,
                        )
                    }
                },
            )
        }
    }
}