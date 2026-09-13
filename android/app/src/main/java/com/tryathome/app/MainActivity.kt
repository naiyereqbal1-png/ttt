package com.tryathome.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tryathome.app.data.CartItem
import com.tryathome.app.data.MockData
import com.tryathome.app.data.Product
import com.tryathome.app.ui.screens.CartScreen
import com.tryathome.app.ui.screens.HomeScreen
import com.tryathome.app.ui.screens.ProductDetailScreen
import com.tryathome.app.ui.theme.Slate900
import com.tryathome.app.ui.theme.TRYatHOMETheme

enum class Screen {
    HOME,
    DETAIL,
    CART
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TRYatHOMETheme {
                TRYatHOMEApp()
            }
        }
    }
}

@Composable
fun TRYatHOMEApp() {
    val context = LocalContext.current
    
    // Global States
    var currentScreen by remember { mutableStateOf(Screen.HOME) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val cartItems = remember { mutableStateListOf<CartItem>() }
    
    // Track bottom nav item selections
    var activeTab by remember { mutableStateOf("HOME") }

    // Navigation callback logic
    val navigateToDetail: (Product) -> Unit = { product ->
        selectedProduct = product
        currentScreen = Screen.DETAIL
    }

    val handleAddToCart: (Product, String, String, Int) -> Unit = { product, size, color, qty ->
        val totalQty = cartItems.sumOf { it.quantity }
        if (totalQty + qty > 5) {
            Toast.makeText(context, "Order Limit Exceeded! Max 5 garments allowed per order.", Toast.LENGTH_LONG).show()
        } else {
            val existingItem = cartItems.find { it.product.id == product.id && it.size == size && it.color == color }
            if (existingItem != null) {
                existingItem.quantity += qty
            } else {
                cartItems.add(CartItem(
                    id = "cart-item-${System.currentTimeMillis()}",
                    product = product,
                    size = size,
                    color = color,
                    quantity = qty
                ))
            }
            Toast.makeText(context, "Added to bag successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    val handleBuyNow: (Product, String, String, Int) -> Unit = { product, size, color, qty ->
        val totalQty = cartItems.sumOf { it.quantity }
        if (totalQty + qty > 5) {
            Toast.makeText(context, "Order Limit Exceeded! Max 5 garments allowed per order.", Toast.LENGTH_LONG).show()
        } else {
            // Add and immediately switch to cart screen
            val existingItem = cartItems.find { it.product.id == product.id && it.size == size && it.color == color }
            if (existingItem == null) {
                cartItems.add(CartItem(
                    id = "cart-item-${System.currentTimeMillis()}",
                    product = product,
                    size = size,
                    color = color,
                    quantity = qty
                ))
            }
            currentScreen = Screen.CART
            activeTab = "CART"
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = activeTab == "HOME" && currentScreen != Screen.CART,
                    onClick = {
                        activeTab = "HOME"
                        currentScreen = Screen.HOME
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Slate900,
                        indicatorColor = Color(0xFFEEF2FF)
                    )
                )

                NavigationBarItem(
                    selected = activeTab == "CATEGORIES",
                    onClick = {
                        activeTab = "CATEGORIES"
                        currentScreen = Screen.HOME
                        Toast.makeText(context, "Categories menu is integrated in Home bar!", Toast.LENGTH_SHORT).show()
                    },
                    icon = { Icon(Icons.Default.GridOn, contentDescription = "Categories") },
                    label = { Text("Categories") }
                )

                NavigationBarItem(
                    selected = activeTab == "WISHLIST",
                    onClick = {
                        activeTab = "WISHLIST"
                        Toast.makeText(context, "Wishlist integrated! Tap Heart icon on products.", Toast.LENGTH_SHORT).show()
                    },
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Wishlist") },
                    label = { Text("Wishlist") }
                )

                NavigationBarItem(
                    selected = activeTab == "CART" || currentScreen == Screen.CART,
                    onClick = {
                        activeTab = "CART"
                        currentScreen = Screen.CART
                    },
                    icon = {
                        BadgedBox(badge = {
                            if (cartItems.isNotEmpty()) {
                                Badge(containerColor = Color(0xFFE11D48)) {
                                    Text(cartItems.sumOf { it.quantity }.toString(), color = Color.White)
                                }
                            }
                        }) {
                            Icon(Icons.Default.ShoppingBag, contentDescription = "Bag")
                        }
                    },
                    label = { Text("Bag") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Slate900,
                        indicatorColor = Color(0xFFEEF2FF)
                    )
                )

                NavigationBarItem(
                    selected = activeTab == "PROFILE",
                    onClick = {
                        activeTab = "PROFILE"
                        Toast.makeText(context, "Secure TRYatHOME Account linked to matching phone OTP!", Toast.LENGTH_LONG).show()
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                Screen.HOME -> {
                    HomeScreen(
                        onProductSelected = navigateToDetail,
                        onAddToCart = { product, sz, col -> handleAddToCart(product, sz, col, 1) },
                        onBuyNow = { product, sz, col -> handleBuyNow(product, sz, col, 1) }
                    )
                }
                Screen.DETAIL -> {
                    selectedProduct?.let { product ->
                        ProductDetailScreen(
                            product = product,
                            onBack = { currentScreen = Screen.HOME },
                            onAddToCart = handleAddToCart,
                            onBuyNow = handleBuyNow
                        )
                    } ?: run {
                        currentScreen = Screen.HOME
                    }
                }
                Screen.CART -> {
                    CartScreen(
                        cartItems = cartItems,
                        onUpdateQuantity = { item, newQty ->
                            if (newQty <= 0) {
                                cartItems.remove(item)
                            } else {
                                val itemIndex = cartItems.indexOf(item)
                                if (itemIndex != -1) {
                                    cartItems[itemIndex] = item.copy(quantity = newQty)
                                }
                            }
                        },
                        onRemoveItem = { cartItems.remove(it) },
                        onCheckout = {
                            Toast.makeText(context, "🎉 Proceeding to secure doorstep trial checkout with Cash on Delivery!", Toast.LENGTH_LONG).show()
                        },
                        onShopMore = {
                            currentScreen = Screen.HOME
                            activeTab = "HOME"
                        }
                    )
                }
            }
        }
    }
}
