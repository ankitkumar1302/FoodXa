package com.ankit.foodxa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.ankit.foodxa.ui.theme.FoodXaLoginScreen
import com.ankit.foodxa.ui.theme.FoodXaSignUpScreenFunctional
import com.ankit.foodxa.ui.theme.FoodXaTheme
import com.ankit.foodxa.ui.theme.FoodXaHomeScreen
import com.ankit.foodxa.ui.theme.PopularFoodScreen
import com.ankit.foodxa.ui.theme.MyCardScreen
import com.ankit.foodxa.ui.theme.CartScreen
import com.ankit.foodxa.ui.theme.AllTransactionsScreen


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
                            }
                        }
                    }
                }
            }
        }
    }
}
