package com.ankit.foodxa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.ankit.foodxa.ui.theme.AllTransactionsScreen
import com.ankit.foodxa.ui.theme.CartScreen
import com.ankit.foodxa.ui.theme.FoodXaHomeScreen
import com.ankit.foodxa.ui.theme.FoodXaLoginScreen
import com.ankit.foodxa.ui.theme.FoodXaSignUpScreenFunctional
import com.ankit.foodxa.ui.theme.FoodXaTheme
import com.ankit.foodxa.ui.theme.HelpSupportScreen
import com.ankit.foodxa.ui.theme.MyCardScreen
import com.ankit.foodxa.ui.theme.NotificationsScreen
import com.ankit.foodxa.ui.theme.OrderTrackingScreen
import com.ankit.foodxa.ui.theme.PopularFoodScreen
import com.ankit.foodxa.ui.theme.ProfileScreen
import com.ankit.foodxa.ui.theme.RestaurantDetailsScreen
import com.ankit.foodxa.ui.theme.SettingsScreen

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
                    var selectedTab by remember { mutableIntStateOf(0) }
                    var currentSubScreen by remember { mutableStateOf<String?>(null) }

                    // Handle back press
                    BackHandler(enabled = true) {
                        when {
                            currentSubScreen != null -> {
                                currentSubScreen = null // Return to main tab
                            }
                            currentScreen == "main" && selectedTab != 0 -> {
                                selectedTab = 0 // Return to home screen
                            }
                            currentScreen == "main" && selectedTab == 0 -> {
                                finish() // Exit app if on home screen
                            }
                        }
                    }

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
                                        when {
                                            currentSubScreen != null -> {
                                                when (currentSubScreen) {
                                                    "order_tracking" -> OrderTrackingScreen(
                                                        onNavigateBack = { currentSubScreen = null },
                                                        onContactSupport = { currentSubScreen = "help_support" }
                                                    )
                                                    "restaurant_details" -> RestaurantDetailsScreen(
                                                        onNavigateBack = { currentSubScreen = null },
                                                        onAddToCart = { selectedTab = 2 },
                                                        onViewMenu = { }
                                                    )
                                                    "notifications" -> NotificationsScreen(
                                                        onNavigateBack = { currentSubScreen = null },
                                                        onNotificationClick = { }
                                                    )
                                                    "settings" -> SettingsScreen(
                                                        onNavigateBack = { currentSubScreen = null },
                                                        onLogout = { currentScreen = "auth" },
                                                        onEditProfile = { },
                                                        onPrivacyPolicy = { },
                                                        onTermsOfService = { },
                                                        onHelpSupport = { currentSubScreen = "help_support" },
                                                        onAbout = { }
                                                    )
                                                    "help_support" -> HelpSupportScreen(
                                                        onNavigateBack = { currentSubScreen = null },
                                                        onContactSupport = { },
                                                        onFAQItemClick = { }
                                                    )
                                                }
                                            }
                                            else -> {
                                                when (selectedTab) {
                                                    0 -> FoodXaHomeScreen(
                                                        onNavigateToPopularFood = { selectedTab = 1 },
                                                        onNavigateToMyCard = { selectedTab = 2 },
                                                        onNotifications = { currentSubScreen = "notifications" }
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
                                                        onLogout = { currentScreen = "auth" },
                                                        onNotifications = { currentSubScreen = "notifications" },
                                                        onSettings = { currentSubScreen = "settings" },
                                                        onOrderTracking = { currentSubScreen = "order_tracking" }
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    // Bottom Navigation Bar with proper padding and animation
                                    AnimatedVisibility(
                                        visible = selectedTab in listOf(0, 5),
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
