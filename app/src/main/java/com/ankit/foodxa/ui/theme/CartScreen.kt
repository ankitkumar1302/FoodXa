package com.ankit.foodxa.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onNavigateBack: () -> Unit = {},
    onCheckout: () -> Unit = {}
) {
    var cartItems by remember {
        mutableStateOf(
            listOf(
                CartItem(
                    food = FoodItem(
                        "Shrimp and Arugula",
                        22.50,
                        4.5f,
                        "https://images.unsplash.com/photo-1600803907087-f56d462fd26b?q=80&w=500",
                        31
                    ),
                    quantity = 1
                ),
                CartItem(
                    food = FoodItem(
                        "Grilled meat",
                        18.99,
                        4.3f,
                        "https://images.unsplash.com/photo-1544025162-d76694265947?q=80&w=500",
                        28
                    ),
                    quantity = 2
                )
            )
        )
    }

    // Payment method state
    var selectedPaymentMethod by remember { mutableIntStateOf(0) }
    var isPaymentMethodExpanded by remember { mutableStateOf(false) }
    val paymentMethods = listOf(
        PaymentMethod("Credit Card", Icons.Default.CreditCard, "**** 1425"),
        PaymentMethod("PayPal", Icons.Default.Payment, "Connected"),
        PaymentMethod("Google Pay", Icons.Default.AccountBalance, "Not Connected")
    )

    // Calculate total price
    val subtotal = cartItems.sumOf { it.food.price * it.quantity }
    val deliveryCharge = 5.00
    val total = subtotal + deliveryCharge

    // Animation states
    val coroutineScope = rememberCoroutineScope()
    var isProcessing by remember { mutableStateOf(false) }
    val buttonWidth by animateFloatAsState(
        targetValue = if (isProcessing) 56f else 1f,
        label = "button_width"
    )

    // Function to handle payment method selection
    fun handlePaymentMethodSelection(index: Int) {
        selectedPaymentMethod = index
        isPaymentMethodExpanded = false // Auto collapse after selection
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
                            text = "My Cart",
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
            bottomBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                    color = Color(0xFF2A2A2A),
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(16.dp)
                    ) {
                        // Payment Methods Section
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF232323)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Payment Method",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    IconButton(
                                        onClick = { isPaymentMethodExpanded = !isPaymentMethodExpanded }
                                    ) {
                                        Icon(
                                            if (isPaymentMethodExpanded) Icons.Default.KeyboardArrowUp
                                            else Icons.Default.KeyboardArrowDown,
                                            contentDescription = "Expand",
                                            tint = Color(0xFFF97316)
                                        )
                                    }
                                }

                                AnimatedVisibility(
                                    visible = isPaymentMethodExpanded,
                                    enter = expandVertically() + fadeIn(),
                                    exit = shrinkVertically() + fadeOut()
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        paymentMethods.forEachIndexed { index, method ->
                                            PaymentMethodItem(
                                                paymentMethod = method,
                                                isSelected = selectedPaymentMethod == index,
                                                onClick = { handlePaymentMethodSelection(index) }
                                            )
                                        }
                                    }
                                }

                                if (!isPaymentMethodExpanded) {
                                    val selected = paymentMethods[selectedPaymentMethod]
                                    PaymentMethodItem(
                                        paymentMethod = selected,
                                        isSelected = true,
                                        onClick = { isPaymentMethodExpanded = true }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Order Summary
                        OrderSummary(
                            subtotal = subtotal,
                            deliveryCharge = deliveryCharge,
                            total = total
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Checkout Button with animation
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    isProcessing = true
                                    kotlinx.coroutines.delay(1500)
                                    isProcessing = false
                                    onCheckout()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .graphicsLayer {
                                    scaleX = buttonWidth
                                },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF97316)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            enabled = !isProcessing && cartItems.isNotEmpty()
                        ) {
                            if (isProcessing) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    "Proceed to Checkout",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            },
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cartItems) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onQuantityChange = { item, newQuantity ->
                            cartItems = cartItems.map {
                                if (it == item) it.copy(quantity = newQuantity)
                                else it
                            }
                        },
                        onRemove = { item ->
                            cartItems = cartItems.filter { it != item }
                        }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onQuantityChange: (CartItem, Int) -> Unit,
    onRemove: (CartItem) -> Unit
) {
    val isHovered by remember { mutableStateOf(false) }
    val isPressed by remember { mutableStateOf(false) }
    
    // Animation values
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val elevation by animateFloatAsState(
        targetValue = if (isHovered) 12f else 8f,
        animationSpec = tween(200),
        label = "elevation"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isHovered) Color(0xFF323232) else Color(0xFF2A2A2A),
        animationSpec = tween(200),
        label = "background"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = Color(0xFFF97316).copy(alpha = if (isHovered) 0.2f else 0.1f)
            )
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationY = if (isHovered) -2f else 0f
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Delete Button with animation
            val isDeleteHovered by remember { mutableStateOf(false) }
            val deleteScale by animateFloatAsState(
                targetValue = if (isDeleteHovered) 1.1f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                label = "delete_scale"
            )

            IconButton(
                onClick = { onRemove(cartItem) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .graphicsLayer {
                        scaleX = deleteScale
                        scaleY = deleteScale
                    }
                    .background(
                        color = if (isDeleteHovered) Color(0xFF2A2A2A) else Color(0xFF1E1E1E),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = Color.Red.copy(alpha = if (isDeleteHovered) 1f else 0.8f),
                    modifier = Modifier.size(16.dp)
                )
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Enhanced Food Image with animation
                    var imageScale by remember { mutableFloatStateOf(1f) }
                    LaunchedEffect(cartItem) {
                        imageScale = 0f
                        animate(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        ) { value, _ -> imageScale = value }
                    }

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer {
                                scaleX = imageScale
                                scaleY = imageScale
                            }
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                spotColor = Color(0xFFF97316).copy(alpha = 0.2f)
                            )
                    ) {
                        AsyncImage(
                            model = cartItem.food.imageUrl,
                            contentDescription = cartItem.food.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Food Details with animations
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = cartItem.food.name,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        // Price and Rating Row with animations
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Animated Price
                            var oldPrice by remember { mutableDoubleStateOf(cartItem.food.price) }
                            val animatedPrice by animateFloatAsState(
                                targetValue = cartItem.food.price.toFloat(),
                                animationSpec = tween(500),
                                label = "price"
                            )
                            
                            LaunchedEffect(cartItem.food.price) {
                                oldPrice = cartItem.food.price
                            }

                            Text(
                                text = "$${String.format(java.util.Locale.US, "%.2f", animatedPrice)}",
                                color = Color(0xFFF97316),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            // Rating with animation
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFF97316),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "${cartItem.food.rating}",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "(${cartItem.food.reviews})",
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        // Quantity Controls with enhanced animations
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFF1E1E1E),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(8.dp)
                        ) {
                            // Quantity Controls
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Animated quantity buttons
                                val decreaseHovered by remember { mutableStateOf(false) }
                                val increaseHovered by remember { mutableStateOf(false) }

                                val decreaseScale by animateFloatAsState(
                                    targetValue = if (decreaseHovered) 1.1f else 1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    ),
                                    label = "decrease_scale"
                                )

                                val increaseScale by animateFloatAsState(
                                    targetValue = if (increaseHovered) 1.1f else 1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    ),
                                    label = "increase_scale"
                                )

                                // Decrease Button
                                Surface(
                                    onClick = {
                                        if (cartItem.quantity > 1) {
                                            onQuantityChange(cartItem, cartItem.quantity - 1)
                                        }
                                    },
                                    modifier = Modifier
                                        .size(28.dp)
                                        .graphicsLayer {
                                            scaleX = decreaseScale
                                            scaleY = decreaseScale
                                        },
                                    shape = CircleShape,
                                    color = Color(0xFF2A2A2A)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Remove,
                                            contentDescription = "Decrease",
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                // Animated quantity
                                var oldQuantity by remember { mutableIntStateOf(cartItem.quantity) }
                                val animatedQuantity by animateIntAsState(
                                    targetValue = cartItem.quantity,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    ),
                                    label = "quantity"
                                )

                                LaunchedEffect(cartItem.quantity) {
                                    oldQuantity = cartItem.quantity
                                }

                                Text(
                                    text = "$animatedQuantity",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                // Increase Button
                                Surface(
                                    onClick = { onQuantityChange(cartItem, cartItem.quantity + 1) },
                                    modifier = Modifier
                                        .size(28.dp)
                                        .graphicsLayer {
                                            scaleX = increaseScale
                                            scaleY = increaseScale
                                        },
                                    shape = CircleShape,
                                    color = Color(0xFFF97316)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Increase",
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            // Animated total price
                            val animatedTotal by animateFloatAsState(
                                targetValue = (cartItem.food.price * cartItem.quantity).toFloat(),
                                animationSpec = tween(500),
                                label = "total"
                            )

                            Text(
                                text = String.format(java.util.Locale.US, "%.2f", animatedTotal),
                                color = Color(0xFFF97316),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OrderSummary(
    subtotal: Double,
    deliveryCharge: Double,
    total: Double
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Subtotal",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                "$${String.format(java.util.Locale.US, "%.2f", subtotal)}",
                color = Color.White,
                fontSize = 16.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Delivery",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Text(
                "$${String.format(java.util.Locale.US, "%.2f", deliveryCharge)}",
                color = Color.White,
                fontSize = 16.sp
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.Gray.copy(alpha = 0.2f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Total",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "$${String.format(java.util.Locale.US, "%.2f", total)}",
                color = Color(0xFFF97316),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun PaymentMethodItem(
    paymentMethod: PaymentMethod,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(
                elevation = if (isSelected) 8.dp else 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = if (isSelected) Color(0xFFF97316).copy(alpha = 0.2f)
                else Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF3A3A3A) else Color(0xFF2A2A2A)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    paymentMethod.icon,
                    contentDescription = paymentMethod.name,
                    tint = if (isSelected) Color(0xFFF97316) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Column {
                    Text(
                        paymentMethod.name,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        paymentMethod.details,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFFF97316),
                    unselectedColor = Color.Gray
                )
            )
        }
    }
}

data class CartItem(
    val food: FoodItem,
    val quantity: Int
)

data class PaymentMethod(
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val details: String
)