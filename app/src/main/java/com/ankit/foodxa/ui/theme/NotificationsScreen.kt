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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onNavigateBack: () -> Unit = {},
    onNotificationClick: (NotificationItem) -> Unit = {}
) {
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationItem(
                    id = "1",
                    title = "Order Delivered!",
                    message = "Your order #FX-2024-001 has been delivered. Enjoy your meal!",
                    time = "2 min ago",
                    type = NotificationType.ORDER,
                    isRead = false,
                    icon = Icons.Default.DeliveryDining,
                    imageUrl = "https://images.unsplash.com/photo-1600803907087-f56d462fd26b?q=80&w=500"
                ),
                NotificationItem(
                    id = "2",
                    title = "Special Offer",
                    message = "Get 20% off on your next order at Tasty Bites. Use code: TASTY20",
                    time = "1 hour ago",
                    type = NotificationType.OFFER,
                    isRead = false,
                    icon = Icons.Default.LocalOffer,
                    imageUrl = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?q=80&w=500"
                ),
                NotificationItem(
                    id = "3",
                    title = "Order Status Update",
                    message = "Your order #FX-2024-002 is being prepared by the chef.",
                    time = "2 hours ago",
                    type = NotificationType.ORDER,
                    isRead = true,
                    icon = Icons.Default.Restaurant,
                    imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947?q=80&w=500"
                ),
                NotificationItem(
                    id = "4",
                    title = "New Restaurant Added",
                    message = "Pizza Palace is now available in your area. Try their signature pizzas!",
                    time = "1 day ago",
                    type = NotificationType.RESTAURANT,
                    isRead = true,
                    icon = Icons.Default.Store,
                    imageUrl = "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?q=80&w=500"
                ),
                NotificationItem(
                    id = "5",
                    title = "Payment Successful",
                    message = "Your payment of $45.99 for order #FX-2024-001 has been processed successfully.",
                    time = "1 day ago",
                    type = NotificationType.PAYMENT,
                    isRead = true,
                    icon = Icons.Default.Payment,
                    imageUrl = null
                )
            )
        )
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
                            text = "Notifications",
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
                        IconButton(onClick = {
                            notifications = notifications.map { it.copy(isRead = true) }
                        }) {
                            Icon(
                                Icons.Default.DoneAll,
                                contentDescription = "Mark all as read",
                                tint = Color(0xFFF97316)
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
            if (notifications.isEmpty()) {
                EmptyNotificationsState()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(notifications) { notification ->
                        NotificationCard(
                            notification = notification,
                            onClick = { onNotificationClick(notification) },
                            onMarkAsRead = {
                                notifications = notifications.map {
                                    if (it.id == notification.id) it.copy(isRead = true) else it
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(
    notification: NotificationItem,
    onClick: () -> Unit,
    onMarkAsRead: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) Color(0xFF2A2A2A) else Color(0xFF3A3A3A)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Notification Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        when (notification.type) {
                            NotificationType.ORDER -> Color(0xFF4CAF50)
                            NotificationType.OFFER -> Color(0xFFFF9800)
                            NotificationType.RESTAURANT -> Color(0xFF2196F3)
                            NotificationType.PAYMENT -> Color(0xFF4CAF50)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = notification.icon,
                    contentDescription = notification.title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Notification Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = notification.title,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = notification.message,
                            color = Color.Gray,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = notification.time,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }

                    // Unread indicator
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF97316))
                        )
                    }
                }

                // Action buttons
                if (!notification.isRead) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TextButton(
                            onClick = onMarkAsRead,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFFF97316)
                            )
                        ) {
                            Text(
                                text = "Mark as read",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        when (notification.type) {
                            NotificationType.ORDER -> {
                                TextButton(
                                    onClick = { },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color(0xFFF97316)
                                    )
                                ) {
                                    Text(
                                        text = "Track Order",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            NotificationType.OFFER -> {
                                TextButton(
                                    onClick = { },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color(0xFFF97316)
                                    )
                                ) {
                                    Text(
                                        text = "Use Offer",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            NotificationType.RESTAURANT -> {
                                TextButton(
                                    onClick = { },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color(0xFFF97316)
                                    )
                                ) {
                                    Text(
                                        text = "View Menu",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }

            // Notification Image (if available)
            notification.imageUrl?.let { imageUrl ->
                Spacer(modifier = Modifier.width(12.dp))
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun EmptyNotificationsState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.NotificationsOff,
            contentDescription = "No notifications",
            tint = Color.Gray,
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No notifications yet",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "We'll notify you about orders, offers, and updates here",
            color = Color.Gray,
            fontSize = 14.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

enum class NotificationType {
    ORDER,
    OFFER,
    RESTAURANT,
    PAYMENT
}

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val imageUrl: String?
)