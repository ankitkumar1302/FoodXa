package com.ankit.foodxa.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailsScreen(
    onNavigateBack: () -> Unit = {},
    onAddToCart: () -> Unit = {},
    onViewMenu: () -> Unit = {}
) {
    val restaurant = Restaurant(
        name = "Tasty Bites",
        rating = 4.5f,
        reviews = 1247,
        deliveryTime = "25-35 min",
        deliveryFee = 2.99,
        minOrder = 15.00,
        cuisine = "Italian, Mediterranean",
        address = "123 Food Street, Cuisine City",
        description = "Authentic Italian cuisine with a modern twist. Fresh ingredients and traditional recipes.",
        imageUrl = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?q=80&w=500",
        isOpen = true,
        popularItems = listOf(
            PopularItem("Margherita Pizza", 18.99, "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?q=80&w=500"),
            PopularItem("Pasta Carbonara", 16.99, "https://images.unsplash.com/photo-1612874742237-6526221588e3?q=80&w=500"),
            PopularItem("Tiramisu", 8.99, "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?q=80&w=500")
        ),
        categories = listOf("Pizza", "Pasta", "Salads", "Desserts", "Beverages")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Restaurant",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1E1E1E),
                        navigationIconContentColor = Color.White,
                        titleContentColor = Color.White
                    ),
                    modifier = Modifier.statusBarsPadding()
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Restaurant Header Image
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        AsyncImage(
                            model = restaurant.imageUrl,
                            contentDescription = restaurant.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        
                        // Status Badge
                        Card(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (restaurant.isOpen) Color(0xFF4CAF50) else Color(0xFFF44336)
                            )
                        ) {
                            Text(
                                text = if (restaurant.isOpen) "Open" else "Closed",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                // Restaurant Info
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = restaurant.name,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFF97316),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = " ${restaurant.rating}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = " (${restaurant.reviews} reviews)",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = restaurant.cuisine,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = restaurant.description,
                            color = Color.White,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                // Delivery Info
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            DeliveryInfoItem(
                                icon = Icons.Default.Schedule,
                                title = "Delivery",
                                subtitle = restaurant.deliveryTime
                            )
                            DeliveryInfoItem(
                                icon = Icons.Default.LocalShipping,
                                title = "Fee",
                                subtitle = "$${String.format(java.util.Locale.US, "%.2f", restaurant.deliveryFee)}"
                            )
                            DeliveryInfoItem(
                                icon = Icons.Default.ShoppingCart,
                                title = "Min Order",
                                subtitle = "$${String.format(java.util.Locale.US, "%.2f", restaurant.minOrder)}"
                            )
                        }
                    }
                }

                // Categories
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Categories",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(restaurant.categories) { category ->
                                CategoryChip(category = category)
                            }
                        }
                    }
                }

                // Popular Items
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Popular Items",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            TextButton(onClick = onViewMenu) {
                                Text(
                                    "View Full Menu",
                                    color = Color(0xFFF97316),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                        
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(restaurant.popularItems) { item ->
                                PopularItemCard(
                                    item = item,
                                    onAddToCart = onAddToCart
                                )
                            }
                        }
                    }
                }

                // Address
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = Color(0xFFF97316),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Address",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = restaurant.address,
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                            IconButton(onClick = { }) {
                                Icon(
                                    Icons.Default.Navigation,
                                    contentDescription = "Navigate",
                                    tint = Color(0xFFF97316)
                                )
                            }
                        }
                    }
                }

                // Action Buttons
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedButtonDefaults.colors(
                                containerColor = Color.Transparent,
                                contentColor = Color(0xFFF97316),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF97316))
                            )
                        ) {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Call",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Call",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        Button(
                            onClick = onAddToCart,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF97316)
                            )
                        ) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Order Now",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Order Now",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeliveryInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color(0xFFF97316),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 12.sp
        )
        Text(
            text = subtitle,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CategoryChip(category: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        )
    ) {
        Text(
            text = category,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun PopularItemCard(
    item: PopularItem,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        )
    ) {
        Column {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${String.format(java.util.Locale.US, "%.2f", item.price)}",
                        color = Color(0xFFF97316),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(
                        onClick = onAddToCart,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF97316))
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add to cart",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

data class Restaurant(
    val name: String,
    val rating: Float,
    val reviews: Int,
    val deliveryTime: String,
    val deliveryFee: Double,
    val minOrder: Double,
    val cuisine: String,
    val address: String,
    val description: String,
    val imageUrl: String,
    val isOpen: Boolean,
    val popularItems: List<PopularItem>,
    val categories: List<String>
)

data class PopularItem(
    val name: String,
    val price: Double,
    val imageUrl: String
)