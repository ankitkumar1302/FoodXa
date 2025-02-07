package com.ankit.foodxa.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCardScreen(
    onNavigateBack: () -> Unit = {},
    onViewAllTransactions: () -> Unit = {}
) {
    var rotation by remember { mutableFloatStateOf(0f) }
    var isCardFlipped by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableIntStateOf(0) }
    var showAddCardDialog by remember { mutableStateOf(false) }
    
    // State for loading indicators
    var isLoadingCards by remember { mutableStateOf(true) }
    var isLoadingTransactions by remember { mutableStateOf(true) }
    var isLoadingBalance by remember { mutableStateOf(true) }
    
    // State for data
    var cards by remember { mutableStateOf<List<CreditCard>>(emptyList()) }
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    var balance by remember { mutableDoubleStateOf(0.0) }
    
    // Coroutine scope for loading data
    val scope = rememberCoroutineScope()
    
    // Load initial data
    LaunchedEffect(Unit) {
        scope.launch {
            // Simulate API call for cards
            delay(800)
            cards = listOf(
                CreditCard(
                    cardHolder = "Dwayne Johnson",
                    cardNumber = "5124 **** **** 1425",
                    expiryDate = "12/25",
                    cvv = "***",
                    cardType = "VISA",
                    backgroundColor = Color(0xFFF97316)
                ),
                CreditCard(
                    cardHolder = "John Doe",
                    cardNumber = "4532 **** **** 8745",
                    expiryDate = "09/26",
                    cvv = "***",
                    cardType = "MASTERCARD",
                    backgroundColor = Color(0xFF9333EA)
                ),
                CreditCard(
                    cardHolder = "Sarah Smith",
                    cardNumber = "6011 **** **** 9632",
                    expiryDate = "03/27",
                    cvv = "***",
                    cardType = "DISCOVER",
                    backgroundColor = Color(0xFF059669)
                )
            )
            isLoadingCards = false
        }
        
        scope.launch {
            // Simulate API call for balance
            delay(500)
            balance = 4857.50
            isLoadingBalance = false
        }
        
        scope.launch {
            // Simulate API call for transactions
            delay(1200)
            transactions = listOf(
                Transaction("Food Delivery", "Today", -24.99, Icons.Default.Restaurant),
                Transaction("Shopping", "Yesterday", -156.78, Icons.Default.ShoppingCart),
                Transaction("Salary", "2 days ago", 3500.00, Icons.Default.AttachMoney),
                Transaction("Groceries", "3 days ago", -89.45, Icons.Default.LocalGroceryStore),
                Transaction("Transport", "4 days ago", -32.50, Icons.Default.DirectionsCar)
            )
            isLoadingTransactions = false
        }
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
                            text = "My Cards",
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
                    actions = {
                        IconButton(onClick = { showAddCardDialog = true }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add Card",
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
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF2A2A2A),
                    tonalElevation = 8.dp
                ) {
                    Box(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(16.dp)
                    ) {
                        if (isLoadingBalance) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = Color(0xFFF97316),
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        "Available Balance",
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        "$${String.format("%.2f", balance)}",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Button(
                                    onClick = { /* Handle payment */ },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFF97316)
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Payment,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "Pay Now",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            },
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card Carousel with loading state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoadingCards) {
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
                                "Loading Cards...",
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 32.dp),
                            state = rememberLazyListState()
                        ) {
                            items(cards.size) { index ->
                                CreditCardView(
                                    card = cards[index],
                                    isSelected = selectedCard == index,
                                    isFlipped = isCardFlipped && selectedCard == index,
                                    onClick = { 
                                        selectedCard = index
                                        isCardFlipped = false
                                    }
                                )
                            }
                        }

                        // Card Selection Indicators with improved spacing
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp) // Increased spacing between indicators
                        ) {
                            cards.forEachIndexed { index, card ->
                                val isSelected = selectedCard == index
                                val indicatorWidth by animateFloatAsState(
                                    targetValue = if (isSelected) 24f else 8f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    ),
                                    label = "indicator_width"
                                )
                                val indicatorAlpha by animateFloatAsState(
                                    targetValue = if (isSelected) 1f else 0.6f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    ),
                                    label = "indicator_alpha"
                                )
                                Box(
                                    modifier = Modifier
                                        .width(indicatorWidth.dp)
                                        .height(8.dp)
                                        .clip(CircleShape)
                                        .background(
                                            color = card.backgroundColor.copy(alpha = indicatorAlpha),
                                            shape = CircleShape
                                        )
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            selectedCard = index
                                            isCardFlipped = false
                                        }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Quick Actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    QuickActionButton(
                        icon = Icons.Default.Lock,
                        text = "Lock Card",
                        onClick = { /* Lock card */ },
                        enabled = !isLoadingCards
                    )
                    QuickActionButton(
                        icon = Icons.Default.Settings,
                        text = "Settings",
                        onClick = { /* Card settings */ },
                        enabled = !isLoadingCards
                    )
                    QuickActionButton(
                        icon = Icons.Default.Info,
                        text = "Details",
                        onClick = { isCardFlipped = !isCardFlipped },
                        enabled = !isLoadingCards
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Recent Transactions Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Recent Transactions",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(
                            onClick = onViewAllTransactions,
                            enabled = !isLoadingTransactions
                        ) {
                            Text(
                                "View All",
                                color = Color(0xFFF97316),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isLoadingTransactions) {
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
                                    "Loading Transactions...",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(start = 0.dp, top = 8.dp, end = 0.dp, bottom = 16.dp)
                            ) {
                                items(transactions.size) { index ->
                                    TransactionItem(
                                        transaction = transactions[index]
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

@Composable
fun CreditCardView(
    card: CreditCard,
    isSelected: Boolean,
    isFlipped: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.9f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale"
    )

    val rotationY by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_flip"
    )

    Card(
        onClick = onClick,
        modifier = Modifier
            .size(width = 320.dp, height = 200.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                cameraDistance = 12f * density
                this.rotationY = rotationY
            }
            .shadow(
                elevation = if (isSelected) 16.dp else 8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = card.backgroundColor.copy(alpha = 0.5f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            card.backgroundColor,
                            card.backgroundColor.copy(alpha = 0.8f)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        this.rotationY = if (isFlipped) 180f else 0f
                    }
            ) {
                if (!isFlipped) {
                    // Front of card content
                    CardFrontContent(card = card)
                } else {
                    // Back of card content
                    CardBackContent(card = card)
                }
            }
        }
    }
}

@Composable
private fun CardFrontContent(card: CreditCard) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Credit Card",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                Icons.Default.Wifi,
                contentDescription = "Wireless",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = card.cardNumber,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "Card Holder",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                Text(
                    card.cardHolder,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "Expires",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                Text(
                    card.expiryDate,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun CardBackContent(card: CreditCard) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.Black.copy(alpha = 0.3f))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(32.dp)
                    .background(
                        Color.White.copy(alpha = 0.9f),
                        RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = card.cvv,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = card.cardType,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
private fun QuickActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "button_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                alpha = if (enabled) 1f else 0.5f
            }
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isPressed = true
                onClick()
                kotlinx.coroutines.MainScope().launch {
                    delay(100)
                    isPressed = false
                }
            }
    ) {
        Surface(
            modifier = Modifier
                .size(56.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    spotColor = Color(0xFFF97316).copy(alpha = 0.2f)
                ),
            shape = CircleShape,
            color = Color(0xFF2A2A2A)
        ) {
            Icon(
                icon,
                contentDescription = text,
                tint = Color(0xFFF97316),
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            color = if (enabled) Color.White else Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    val isHovered by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isHovered) 8.dp else 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color(0xFFF97316).copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = Color(0xFF1E1E1E)
                ) {
                    Icon(
                        transaction.icon,
                        contentDescription = null,
                        tint = Color(0xFFF97316),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
                Column {
                    Text(
                        text = transaction.merchantName,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = transaction.date,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
            Text(
                text = String.format("$%.2f", transaction.amount),
                color = if (transaction.amount < 0) Color.Red else Color(0xFF4CAF50),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class Transaction(
    val merchantName: String,
    val date: String,
    val amount: Double,
    val icon: ImageVector
)

data class CreditCard(
    val cardHolder: String,
    val cardNumber: String,
    val expiryDate: String,
    val cvv: String,
    val cardType: String,
    val backgroundColor: Color
) 