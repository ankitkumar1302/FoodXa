package com.ankit.foodxa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.ankit.foodxa.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            FoodXaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF1E1E1E)
                ) {
                    var currentScreen by remember { mutableStateOf("auth") }
                    var showLogin by remember { mutableStateOf(false) }
                    var selectedTab by remember { mutableStateOf(0) }
                    
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (currentScreen) {
                            "auth" -> {
                                if (showLogin) {
                                    FoodXaLoginScreen(
                                        onSignUpClick = { showLogin = false },
                                        onLoginSuccess = { currentScreen = "main" }
                                    )
                                } else {
                                    FoodXaSignUpScreenFunctional(
                                        onLoginClick = { showLogin = true },
                                        onSignUpSuccess = { currentScreen = "main" }
                                    )
                                }
                            }
                            "main" -> {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    // Status bar scrim for all screens
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color(0xFF1E1E1E),
                                                        Color(0xFF1E1E1E).copy(alpha = 0f)
                                                    )
                                                )
                                            )
                                    )

                                    // Main content
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        when (selectedTab) {
                                            0 -> FoodXaHomeScreen(
                                                onNavigateToPopularFood = { selectedTab = 1 },
                                                onNavigateToMyCard = { selectedTab = 2 }
                                            )
                                            1 -> PopularFoodScreen(
                                                onNavigateBack = { selectedTab = 0 }
                                            )
                                            2 -> CartScreen(
                                                onNavigateBack = { selectedTab = 0 },
                                                onCheckout = { selectedTab = 3 }
                                            )
                                            3 -> MyCardScreen(
                                                onNavigateBack = { selectedTab = 2 },
                                                onViewAllTransactions = { selectedTab = 4 }
                                            )
                                            4 -> AllTransactionsScreen(
                                                onNavigateBack = { selectedTab = 3 }
                                            )
                                            5 -> ProfileScreen(
                                                onNavigateBack = { selectedTab = 0 },
                                                onLogout = { currentScreen = "auth" }
                                            )
                                        }
                                    }

                                    // Bottom Navigation Bar with proper padding and animation
                                    AnimatedVisibility(
                                        visible = selectedTab in listOf(0, 5), // Show only on home and profile screens
                                        modifier = Modifier.align(Alignment.BottomCenter),
                                        enter = slideInVertically { it },
                                        exit = slideOutVertically { it }
                                    ) {
                                        Surface(
                                            color = Color(0xFF2A2A2A),
                                            tonalElevation = 8.dp,
                                            shadowElevation = 8.dp,
                                        ) {
                                            Column {
                                                NavigationBar(
                                                    modifier = Modifier.navigationBarsPadding(),
                                                    containerColor = Color.Transparent,
                                                    tonalElevation = 0.dp
                                                ) {
                                                    BottomNavItem.entries.forEach { item ->
                                                        NavigationBarItem(
                                                            selected = selectedTab == item.index,
                                                            onClick = { selectedTab = item.index },
                                                            icon = {
                                                                Icon(
                                                                    imageVector = item.icon,
                                                                    contentDescription = item.label
                                                                )
                                                            },
                                                            label = {
                                                                Text(text = item.label)
                                                            },
                                                            colors = NavigationBarItemDefaults.colors(
                                                                selectedIconColor = Color(0xFFF97316),
                                                                selectedTextColor = Color(0xFFF97316),
                                                                unselectedIconColor = Color.Gray,
                                                                unselectedTextColor = Color.Gray,
                                                                indicatorColor = Color(0xFF1E1E1E)
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class BottomNavItem(
    val index: Int,
    val label: String,
    val icon: ImageVector
) {
    Home(0, "Home", Icons.Default.Home),
    Explore(1, "Explore", Icons.Default.Search),
    Cart(2, "Cart", Icons.Default.ShoppingCart),
    Profile(5, "Profile", Icons.Default.Person)
}
