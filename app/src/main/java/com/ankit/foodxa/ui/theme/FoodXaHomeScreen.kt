package com.ankit.foodxa.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun FoodXaHomeScreen(
    onNavigateToPopularFood: () -> Unit = {},
    onNavigateToMyCard: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val searchFocusRequester = remember { FocusRequester() }

    val categories = listOf(
        Category("Food", Icons.Default.Fastfood),
        Category("Juice", Icons.Default.LocalDrink),
        Category("Dessert", Icons.Default.Cake)
    )

    val popularFoods = listOf(
        FoodItem(
            "Shrimp and Arugula",
            22.50,
            4.5f,
            "https://images.unsplash.com/photo-1600803907087-f56d462fd26b?q=80&w=500",
            31
        ),
        FoodItem(
            "Grilled meat",
            18.99,
            4.3f,
            "https://images.unsplash.com/photo-1544025162-d76694265947?q=80&w=500",
            28
        ),
        FoodItem(
            "Caesar Salad",
            15.99,
            4.4f,
            "https://images.unsplash.com/photo-1546793665-c74683f339c1?q=80&w=500",
            42
        ),
        FoodItem(
            "Pasta Carbonara",
            19.99,
            4.6f,
            "https://images.unsplash.com/photo-1612874742237-6526221588e3?q=80&w=500",
            35
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                    color = Color(0xFF2A2A2A),
                    tonalElevation = 8.dp
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        modifier = Modifier.navigationBarsPadding()
                    ) {
                        NavigationBarItem(
                            selected = true,
                            onClick = { },
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            label = { Text("Home") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFF97316),
                                selectedTextColor = Color(0xFFF97316),
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = onNavigateToPopularFood,
                            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                            label = { Text("Search") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFF97316),
                                selectedTextColor = Color(0xFFF97316),
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = onNavigateToMyCard,
                            icon = {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Cart"
                                )
                            },
                            label = { Text("Cart") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFF97316),
                                selectedTextColor = Color(0xFFF97316),
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                            label = { Text("Profile") },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFFF97316),
                                selectedTextColor = Color(0xFFF97316),
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(top = 16.dp)
                    ) {
                        // Top Bar with better spacing
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Hello, Dwayne",
                                    color = Color(0xFFF97316),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Find your food",
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2A2A2A))
                            ) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Search Bar with better styling
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(top = 24.dp, bottom = 16.dp)
                                .focusRequester(searchFocusRequester)
                                .height(56.dp),
                            placeholder = {
                                Text(
                                    "Search your favorite food",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            },
                            textStyle = TextStyle(
                                color = Color.White,
                                fontSize = 16.sp
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color(0xFFF97316),
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            trailingIcon = if (searchQuery.isNotEmpty()) {
                                {
                                    IconButton(onClick = {
                                        searchQuery = ""
                                        focusManager.clearFocus()
                                    }) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Clear search",
                                            tint = Color.Gray
                                        )
                                    }
                                }
                            } else null,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFF2A2A2A),
                                unfocusedContainerColor = Color(0xFF2A2A2A),
                                focusedBorderColor = Color(0xFFF97316),
                                unfocusedBorderColor = Color.Transparent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color(0xFFF97316),
                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    focusManager.clearFocus()
                                }
                            )
                        )
                    }
                }

                // Categories with better spacing
                item {
                    Text(
                        text = "Categories",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.padding(bottom = 24.dp)
                    ) {
                        items(categories) { category ->
                            CategoryItem(category)
                        }
                    }
                }

                // Popular Foods Header with better spacing
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Popular Foods",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(onClick = onNavigateToPopularFood) {
                            Text(
                                "View All",
                                color = Color(0xFFF97316),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Food Grid with better spacing
                items(popularFoods.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { food ->
                            Box(modifier = Modifier.weight(1f)) {
                                FoodCard(
                                    food = food,
                                    onAddToCart = onNavigateToMyCard
                                )
                            }
                        }
                        if (rowItems.size == 1) {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.name,
                tint = Color(0xFFF97316),
                modifier = Modifier.size(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Composable
fun FoodCard(
    food: FoodItem,
    onAddToCart: () -> Unit = {}
) {
    Card(
        onClick = onAddToCart,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                AsyncImage(
                    model = food.imageUrl,
                    contentDescription = food.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color(0xFFF97316),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = food.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFF97316),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = " ${food.rating}",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Text(
                        text = " (${food.reviews})",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = "$${String.format(java.util.Locale.US, "%.2f", food.price)}",
                    color = Color(0xFFF97316),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

data class Category(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

data class FoodItem(
    val name: String,
    val price: Double,
    val rating: Float,
    val imageUrl: String,
    val reviews: Int
) 