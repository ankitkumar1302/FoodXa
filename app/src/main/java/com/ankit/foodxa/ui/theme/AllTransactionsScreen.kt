package com.ankit.foodxa.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTransactionsScreen(
    onNavigateBack: () -> Unit = {}
) {
    var isLoading by remember { mutableStateOf(true) }
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "Income", "Expenses")

    // Animation states
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Stats for the summary card
    var totalIncome by remember { mutableDoubleStateOf(0.0) }
    var totalExpenses by remember { mutableDoubleStateOf(0.0) }
    var isStatsVisible by remember { mutableStateOf(false) }

    // Calculate stats when transactions change
    LaunchedEffect(transactions) {
        totalIncome = transactions.filter { it.amount > 0 }.sumOf { it.amount }
        totalExpenses = transactions.filter { it.amount < 0 }.sumOf { it.amount.absoluteValue }
        delay(300) // Delay to show stats after transactions load
        isStatsVisible = true
    }

    // Load transactions data
    LaunchedEffect(Unit) {
        scope.launch {
            delay(1000) // Simulate API call
            transactions = listOf(
                Transaction("Food Delivery", "Today", -24.99, Icons.Default.Restaurant),
                Transaction("Shopping", "Yesterday", -156.78, Icons.Default.ShoppingCart),
                Transaction("Salary", "2 days ago", 3500.00, Icons.Default.AttachMoney),
                Transaction("Groceries", "3 days ago", -89.45, Icons.Default.LocalGroceryStore),
                Transaction("Transport", "4 days ago", -32.50, Icons.Default.DirectionsCar),
                Transaction("Freelance Work", "5 days ago", 750.00, Icons.Default.Work),
                Transaction("Coffee", "6 days ago", -4.50, Icons.Default.Coffee),
                Transaction("Movie Tickets", "1 week ago", -30.00, Icons.Default.Movie),
                Transaction(
                    "Investment Return",
                    "1 week ago",
                    125.00,
                    Icons.AutoMirrored.Filled.TrendingUp
                ),
                Transaction("Gym Membership", "2 weeks ago", -50.00, Icons.Default.FitnessCenter)
            )
            isLoading = false
        }
    }

    // Filter transactions
    val filteredTransactions = when (selectedFilter) {
        "Income" -> transactions.filter { it.amount > 0 }
        "Expenses" -> transactions.filter { it.amount < 0 }
        else -> transactions
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
                            text = "All Transactions",
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
                // Summary Card with animations
                AnimatedVisibility(
                    visible = isStatsVisible && !isLoading,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(20.dp),
                                spotColor = Color(0xFFF97316).copy(alpha = 0.2f)
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2A2A2A)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                "Transaction Summary",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Income Summary
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.TrendingUp,
                                            contentDescription = null,
                                            tint = Color(0xFF4CAF50),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            "Income",
                                            color = Color.Gray,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Text(
                                        formatAmount(totalIncome),
                                        color = Color(0xFF4CAF50),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                // Expenses Summary
                                Column(horizontalAlignment = Alignment.End) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.TrendingDown,
                                            contentDescription = null,
                                            tint = Color.Red,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            "Expenses",
                                            color = Color.Gray,
                                            fontSize = 14.sp
                                        )
                                    }
                                    Text(
                                        formatAmount(totalExpenses),
                                        color = Color.Red,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // Enhanced Filter Chips with animations
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filters.forEach { filter ->
                        val isSelected = selectedFilter == filter
                        Card(
                            onClick = {
                                selectedFilter = filter
                                scope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFFF97316) else Color(0xFF2A2A2A)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = when (filter) {
                                        "Income" -> Icons.AutoMirrored.Filled.TrendingUp
                                        "Expenses" -> Icons.AutoMirrored.Filled.TrendingDown
                                        else -> Icons.Default.Receipt
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = if (isSelected) Color.White else Color.Gray
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = filter,
                                    fontSize = 14.sp,
                                    color = if (isSelected) Color.White else Color.Gray,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Transactions List with loading state and animations
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        LoadingAnimation()
                    } else if (filteredTransactions.isEmpty()) {
                        EmptyStateMessage(filter = selectedFilter)
                    } else {
                        LazyColumn(
                            state = listState,
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(
                                items = filteredTransactions,
                                key = { "${it.merchantName}${it.date}${it.amount}" }
                            ) { transaction ->
                                TransactionItem(
                                    transaction = transaction,
                                    modifier = Modifier.animateItem(
                                        fadeInSpec = null,
                                        fadeOutSpec = null,
                                        placementSpec = spring(
                                            stiffness = Spring.StiffnessMediumLow,
                                            visibilityThreshold = IntOffset.VisibilityThreshold
                                        )
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

@Composable
private fun LoadingAnimation() {
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
}

@Composable
private fun EmptyStateMessage(filter: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(32.dp)
    ) {
        Icon(
            when (filter) {
                "Income" -> Icons.AutoMirrored.Filled.TrendingUp
                "Expenses" -> Icons.AutoMirrored.Filled.TrendingDown
                else -> Icons.Default.Receipt
            },
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = when (filter) {
                "Income" -> "No income transactions found"
                "Expenses" -> "No expense transactions found"
                else -> "No transactions found"
            },
            color = Color.Gray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Try adjusting your filters",
            color = Color.Gray.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
    }
}

private fun formatAmount(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale.US)
    return format.format(amount)
} 