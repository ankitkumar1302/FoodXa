package com.ankit.foodxa.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularFoodScreen(
    onNavigateBack: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val searchFocusRequester = remember { FocusRequester() }
    
    // Loading states
    var isLoadingFoods by remember { mutableStateOf(true) }
    var isSearching by remember { mutableStateOf(false) }
    
    // Foods state
    var popularFoods by remember { mutableStateOf<List<FoodItem>>(emptyList()) }
    
    // Coroutine scope for loading data
    val scope = rememberCoroutineScope()
    
    // Load initial data
    LaunchedEffect(Unit) {
        scope.launch {
            // Simulate API call for popular foods
            delay(1200)
            popularFoods = listOf(
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
                ),
                FoodItem(
                    "Margherita Pizza",
                    16.99,
                    4.7f,
                    "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?q=80&w=500",
                    56
                ),
                FoodItem(
                    "Sushi Roll",
                    24.99,
                    4.8f,
                    "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?q=80&w=500",
                    47
                ),
                FoodItem(
                    "Beef Burger",
                    14.99,
                    4.5f,
                    "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?q=80&w=500",
                    62
                ),
                FoodItem(
                    "Greek Salad",
                    13.99,
                    4.3f,
                    "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?q=80&w=500",
                    29
                )
            )
            isLoadingFoods = false
        }
    }

    // Filter foods based on search query with debounce
    var debouncedSearchQuery by remember { mutableStateOf("") }
    var filteredFoods by remember { mutableStateOf(popularFoods) }
    
    LaunchedEffect(searchQuery) {
        isSearching = true
        delay(300) // Debounce delay
        debouncedSearchQuery = searchQuery
    }
    
    LaunchedEffect(debouncedSearchQuery, popularFoods) {
        filteredFoods = if (debouncedSearchQuery.isEmpty()) {
            popularFoods
        } else {
            popularFoods.filter {
                it.name.contains(debouncedSearchQuery, ignoreCase = true)
            }
        }
        isSearching = false
    }

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
                            text = "Popular Foods",
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
            },
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Enhanced Search Bar with loading indicator
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp, bottom = 16.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
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
                            if (isSearching) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color(0xFFF97316),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color(0xFFF97316),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
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

                // Loading State or Food Grid
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoadingFoods) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFFF97316),
                                modifier = Modifier.size(48.dp),
                                strokeWidth = 4.dp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Loading Popular Foods...",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    } else if (filteredFoods.isEmpty() && !isSearching) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.SearchOff,
                                contentDescription = "No Results",
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "No foods found",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(filteredFoods.size) { index ->
                                PopularFoodCard(food = filteredFoods[index])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PopularFoodCard(
    food: FoodItem,
    modifier: Modifier = Modifier
) {
    var isImageLoading by remember { mutableStateOf(true) }
    val isHovered by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = if (isHovered) 16.dp else 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color(0xFFF97316).copy(alpha = if (isHovered) 0.2f else 0.1f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = food.imageUrl,
                    contentDescription = food.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop,
                    onLoading = { isImageLoading = true },
                    onSuccess = { isImageLoading = false }
                )
                
                if (isImageLoading) {
                    CircularProgressIndicator(
                        color = Color(0xFFF97316),
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
                
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
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
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